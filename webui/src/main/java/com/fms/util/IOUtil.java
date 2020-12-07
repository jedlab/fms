package com.fms.util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class IOUtil
{

    public static void fastCopy(final InputStream src, final OutputStream dest) throws IOException
    {
        final ReadableByteChannel inputChannel = Channels.newChannel(src);
        final WritableByteChannel outputChannel = Channels.newChannel(dest);
        fastCopy(inputChannel, outputChannel);
    }

    public static void fastCopy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException
    {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);

        while (src.read(buffer) != -1)
        {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }

        buffer.flip();

        while (buffer.hasRemaining())
        {
            dest.write(buffer);
        }
    }

    public static String humanReadableByteCountBin(long bytes)
    {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024)
        {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10)
        {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }
}