package Project;

public final class TimeUtils {
    public static String intervalToString(long timeInterval) {
        if(timeInterval < 1_000)
            return "1초 이내";
        if(timeInterval < 60_000)
            return (timeInterval / 1_000) + " 초";
        return "약 " + (timeInterval / 60_000) + " 분";
    }
}
