package com.yamaha.model.editor.Style;

/**
 * A StyleChannel represents one of the eight channels of a style:
 * <ol>
 * <li>RHY1<li>RHY2<li>BASS<li>CHD1<li>CHD2<li>PAD<li>PHR1<li>PHR2
 * </ol>
 * @author Dominic Plein
 */
public enum StyleChannel {
    RHY1(1),
    RHY2(2),
    BASS(3),
    CHD1(4),
    CHD2(5),
    PAD(6),
    PHR1(7),
    PHR2(8);

    private int channelNumber;

    StyleChannel(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    /**
     * @return the channel number of this StyleChannel
     */
    public int getChannelNumber() {
        return channelNumber;
    }

    /**
     * @param channelNumber the channel number of the StyleChannel
     * @return the StyleChannel for a given channel number (in range of 1 to 8)
     * @throws IllegalArgumentException if the channel number is not in range of 1 to 8
     */
    public static StyleChannel getChannel(int channelNumber) throws IllegalArgumentException {
        if (channelNumber < 1 || channelNumber > 8)
            throw new IllegalArgumentException("The channel number has to be in range of 1 to 8.");
        for (StyleChannel channel : StyleChannel.values()) {
            if (channel.channelNumber == channelNumber)
                return channel;
        }
        return null;
    }

}