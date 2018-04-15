package com.yamaha.model.editor;

/**
 * A StyleChannel represents one of the eight channels of a style:
 * <ol>
 * <li>RHY1<li>RHY2<li>BASS<li>CHD1<li>CHD2<li>PAD<li>PHR1<li>PHR2
 * </ol>
 * @author Dominic Plein
 * @version 1.0
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
     * Returns the channel number of the StyleChannel.
     * @return the channel number of the StyleChannel.
     */
    public int getChannelNumber() {
        return channelNumber;
    }

    /**
     * Returns the StyleChannel for a given channel number (1-8).
     * @param channelNumber the channel number of the StyleChannel
     * @return the StyleChannel for a given channel number
     */
    public static StyleChannel getChannel(int channelNumber)  {
        for (StyleChannel channel : StyleChannel.values()) {
            if (channel.getChannelNumber() == channelNumber) // if the current channel number is equal to the given channel number
                return channel;
        }
        return null; // e. g. if channel number is 0 or 9 etc.
    }

}