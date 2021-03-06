package info.nightscout.androidaps.plugins.pump.omnipod.comm.message.response.podinfo;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import info.nightscout.androidaps.plugins.pump.omnipod.defs.FaultEventType;
import info.nightscout.androidaps.plugins.pump.omnipod.defs.PodInfoType;

public class PodInfoFaultAndInitializationTime extends PodInfo {
    private static final int MINIMUM_MESSAGE_LENGTH = 17;
    private final FaultEventType faultEventType;
    private final Duration timeFaultEvent;
    private final DateTime initializationTime;

    public PodInfoFaultAndInitializationTime(byte[] encodedData) {
        super(encodedData);

        if (encodedData.length < MINIMUM_MESSAGE_LENGTH) {
            throw new IllegalArgumentException("Not enough data");
        }

        faultEventType = FaultEventType.fromByte(encodedData[1]);
        timeFaultEvent = Duration.standardMinutes(((encodedData[2] & 0b1) << 8) + encodedData[3]);
        // We ignore time zones here because we don't keep the time zone in which the pod was initially set up
        // Which is fine because we don't use the initialization time for anything important anyway
        initializationTime = new DateTime(2000 + encodedData[14], encodedData[12], encodedData[13], encodedData[15], encodedData[16]);
    }

    @Override
    public PodInfoType getType() {
        return PodInfoType.FAULT_AND_INITIALIZATION_TIME;
    }

    public FaultEventType getFaultEventType() {
        return faultEventType;
    }

    public Duration getTimeFaultEvent() {
        return timeFaultEvent;
    }

    public DateTime getInitializationTime() {
        return initializationTime;
    }

    @Override
    public String toString() {
        return "PodInfoFaultAndInitializationTime{" +
                "faultEventType=" + faultEventType +
                ", timeFaultEvent=" + timeFaultEvent +
                ", initializationTime=" + initializationTime +
                '}';
    }
}
