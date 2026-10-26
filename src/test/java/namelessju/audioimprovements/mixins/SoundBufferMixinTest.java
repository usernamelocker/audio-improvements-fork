package namelessju.audioimprovements.mixins;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.AudioFormat;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.*;

class SoundBufferMixinTest
{
    @Test
    void downmixesSignedStereoAndUpdatesFrameSize() throws Exception
    {
        for (ByteOrder order : new ByteOrder[] {ByteOrder.LITTLE_ENDIAN, ByteOrder.BIG_ENDIAN})
        {
            ByteBuffer stereo = ByteBuffer.allocate(8).order(order);
            stereo.putShort((short) 1000).putShort((short) -500);
            stereo.putShort(Short.MAX_VALUE).putShort(Short.MAX_VALUE).flip();
            SoundBufferMixin buffer = new SoundBufferMixin();
            set(buffer, "data", stereo);
            set(buffer, "format", new AudioFormat(44100, 16, 2, true, order == ByteOrder.BIG_ENDIAN));

            buffer.audioimprovements$convertStereoToMono();

            ByteBuffer mono = (ByteBuffer) get(buffer, "data");
            AudioFormat format = (AudioFormat) get(buffer, "format");
            assertEquals(4, mono.remaining());
            assertEquals(250, mono.getShort());
            assertEquals(Short.MAX_VALUE, mono.getShort());
            assertEquals(1, format.getChannels());
            assertEquals(2, format.getFrameSize());
            assertEquals(order == ByteOrder.BIG_ENDIAN, format.isBigEndian());
        }
    }

    @Test
    void leavesUnsupportedSampleFormatUntouched() throws Exception
    {
        SoundBufferMixin buffer = new SoundBufferMixin();
        ByteBuffer data = ByteBuffer.allocate(2);
        AudioFormat format = new AudioFormat(44100, 8, 2, false, false);
        set(buffer, "data", data);
        set(buffer, "format", format);
        buffer.audioimprovements$convertStereoToMono();
        assertSame(data, get(buffer, "data"));
        assertSame(format, get(buffer, "format"));
    }

    private static Object get(Object object, String name) throws Exception
    {
        Field field = SoundBufferMixin.class.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(object);
    }

    private static void set(Object object, String name, Object value) throws Exception
    {
        Field field = SoundBufferMixin.class.getDeclaredField(name);
        field.setAccessible(true);
        field.set(object, value);
    }
}
