package kg.eldar.dms.util;

import org.apache.commons.lang3.time.FastDateFormat;

public class DateUtil {
    public static final FastDateFormat standard = FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss");
    public static final FastDateFormat standardYear = FastDateFormat.getInstance("dd.MM.yy HH:mm:ss");
    public static final FastDateFormat standardNoSeconds = FastDateFormat.getInstance("dd.MM.yyyy HH:mm");
    public static final FastDateFormat standardNoTime = FastDateFormat.getInstance("dd.MM.yyyy");
    public static final FastDateFormat standardMillis = FastDateFormat.getInstance("dd.MM.yyyy HH:mm:ss.SSS");
    public static final FastDateFormat standardYearFirst = FastDateFormat.getInstance("yyyy.MM.dd HH:mm:ss");
    public static final FastDateFormat frontMillis = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS"); // С фронта дата приходит обычно в таком формате
    public static final FastDateFormat frontStandard = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss"); // С фронта дата приходит обычно в таком формате
    public static final FastDateFormat frontNoSeconds = FastDateFormat.getInstance("yyyy-MM-dd HH:mm"); // С фронта дата приходит обычно в таком формате
    public static final FastDateFormat frontNoTime = FastDateFormat.getInstance("yyyy-MM-dd"); // С фронта дата приходит обычно в таком формате
    public static final FastDateFormat noTime = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat year = FastDateFormat.getInstance("YYYY");
    public static final FastDateFormat month = FastDateFormat.getInstance("MM");
    public static final FastDateFormat day = FastDateFormat.getInstance("dd");
    public static final FastDateFormat monitoringDayFirst = FastDateFormat.getInstance("dd.MM.yy HH:mm:ss");
    public static final FastDateFormat draftFormatter = FastDateFormat.getInstance("dd.MM.yyyy, HH:mm:ss");
    public static final FastDateFormat bookkeepingDateFormat = FastDateFormat.getInstance("dd.MM.yyyy");
    public static final FastDateFormat jiraDate = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
}
