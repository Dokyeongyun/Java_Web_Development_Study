package CustomTag.tag;

import org.apache.commons.lang3.StringUtils;
import org.apache.taglibs.standard.tag.common.core.Util;
import org.apache.taglibs.standard.tag.common.fmt.SetLocaleSupport;
import org.apache.taglibs.standard.tag.common.fmt.TimeZoneSupport;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Function;

public class FormatDateTag extends TagSupport {

    // 아파치/글래스피시 JSTL 구현에 리플렉션을 통해 접근하는 것을 효과적으로 수행하기 위한 정적필드 선언
    private static final Method GET_LOCALE_METHOD;
    private static final Method GET_TIME_ZONE_METHOD;

    static {
        try {
            GET_LOCALE_METHOD = SetLocaleSupport.class.getDeclaredMethod(
                    "getFormattingLocale", PageContext.class, Tag.class,
                    boolean.class,boolean.class
            );
            GET_LOCALE_METHOD.setAccessible(true);
            GET_TIME_ZONE_METHOD = TimeZoneSupport.class.getDeclaredMethod(
                    "getTimeZone", PageContext.class, Tag.class
            );
            GET_TIME_ZONE_METHOD.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    protected Object value;
    protected Type type;
    protected Style dateStyle;
    protected Style timeStyle;
    protected boolean timeFirst;
    protected String separateDateTimeWith;
    protected String pattern;
    protected TimeZone timeZone;
    protected String var;
    protected int scope;

    // 생성자에서 init 메서드 호출해 모든 태그 속성을 기본값으로 재설정
    public FormatDateTag() {
        super();
        init();
    }

    private void init() {
        this.type = Type.DATE;
        this.dateStyle = this.timeStyle = Style.DEFAULT;
        this.timeFirst = false;
        this.separateDateTimeWith = " ";
        this.pattern = this.var = null;
        this.timeZone = null;
        this.scope = PageContext.PAGE_SCOPE;
    }

    // 컨테이너는 태그를 사용할 때마다 중간중간에 release 메서드를 호출하여 모든 속성을 재설정
    @Override
    public void release() {
        this.init();
    }


    // 태그를 닫을 준비가 되면 컨테이너가 호출하는 메서드
    @Override
    public int doEndTag() throws JspException {
        // 날짜가 null 이면 아무것도 출력하지 않고 범위 변수를 제거
        if(this.value == null) {
            if(this.var != null)
                pageContext.removeAttribute(this.var, this.scope);
            return Tag.EVAL_PAGE;
        }

        String formatted;
        Locale locale = this.getLocale();
        if(locale != null) {
            if(this.timeZone == null)
                this.timeZone = this.getTimeZone();
            if(this.timeZone == null)
                this.timeZone = TimeZone.getDefault();

            // 날짜 형식에 따라 formatDate 메서드 호출
            if(this.value instanceof Date)
                formatted = this.formatDate((Date)this.value, locale);
            else if(this.value instanceof Calendar)
                formatted = this.formatDate(((Calendar)this.value).getTime(), locale);
            else if(this.value instanceof TemporalAccessor)
                formatted = this.formatDate((TemporalAccessor)this.value, locale);
            else
                throw new JspTagException("Unsupported date type " + this.value.getClass().getCanonicalName() + ".");
        }
        else {
            formatted = this.value.toString();
        }

        if(this.var != null)
            this.pageContext.setAttribute(this.var, formatted, this.scope);
        else {
            try {
                this.pageContext.getOut().print(formatted);
            } catch (IOException e) {
                throw new JspTagException(e.toString(), e);
            }
        }

        return Tag.EVAL_PAGE;
    }

    private String formatDate(Date value, Locale locale) {
        if(this.pattern != null) {
            SimpleDateFormat format = new SimpleDateFormat(this.pattern, locale);
            format.setTimeZone(this.timeZone);
            return format.format(value);
        }

        Function<Date, String> dateFormat = null;
        Function<Date, String> timeFormat = null;

        if(this.type == Type.DATE || this.type == Type.BOTH) {
            DateFormat format = DateFormat
                    .getDateInstance(this.dateStyle.getDateFormat(), locale);
            format.setTimeZone(this.timeZone);
            dateFormat = format::format;
        }
        if(this.type == Type.TIME || this.type == Type.BOTH) {
            DateFormat format = DateFormat
                    .getTimeInstance(this.timeStyle.getDateFormat(), locale);
            format.setTimeZone(this.timeZone);
            timeFormat = format::format;
        }

        return this.formatDate(dateFormat, timeFormat, value);
    }

    private String formatDate(TemporalAccessor value, Locale locale) {
        ZoneId zoneId = this.timeZone.toZoneId();

        if(this.pattern != null)
            return DateTimeFormatter.ofPattern(this.pattern, locale)
                    .withZone(zoneId).format(value);

        Function<TemporalAccessor, String> dateFormat = null;
        Function<TemporalAccessor, String> timeFormat = null;

        if(this.type == Type.DATE || this.type == Type.BOTH)
            dateFormat = DateTimeFormatter
                    .ofLocalizedDate(this.dateStyle.getFormatStyle())
                    .withLocale(locale).withZone(zoneId)::format;
        if(this.type == Type.TIME || this.type == Type.BOTH)
            timeFormat = DateTimeFormatter
                    .ofLocalizedTime(this.timeStyle.getFormatStyle())
                    .withLocale(locale).withZone(zoneId)::format;

        return this.formatDate(dateFormat, timeFormat, value);
    }

    private <T> String formatDate(Function<T, String> dateFormat, Function<T, String> timeFormat, T value) {
        String formatted = "";
        if(timeFormat != null && this.timeFirst) {
            formatted += timeFormat.apply(value);
            if(dateFormat != null)
                formatted += this.separateDateTimeWith;
        }
        if(dateFormat != null)
            formatted += dateFormat.apply(value);
        if(timeFormat != null && !this.timeFirst) {
            if(dateFormat != null)
                formatted += this.separateDateTimeWith;
            formatted += timeFormat.apply(value);
        }
        return formatted;
    }

    // 정적 필드를 이용해 아파치 클래스의 protected 메서드 호출
    private Locale getLocale() throws JspTagException {
        try {
            return (Locale)GET_LOCALE_METHOD.invoke(null, this.pageContext, this, true, true);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new JspTagException(e.toString(), e);
        }
    }
    // 정적 필드를 이용해 아파치 클래스의 protected 메서드 호출
    private TimeZone getTimeZone() throws JspTagException {
        try {
            return (TimeZone)GET_TIME_ZONE_METHOD.invoke(null, this.pageContext, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new JspTagException(e.toString(), e);
        }
    }




    // setter
    // 아파치 커먼즈 Lang의 StringUtils 클래스를 사용하여 문자열 속성이 null 이거나 비어있는지 쉽게 확인
    public void setValue(Object value)
    {
        this.value = value;
    }

    public void setType(String type) {
        this.type = StringUtils.isBlank(type) ? null : Type.valueOf(type.toUpperCase());
    }

    public void setDateStyle(String style) {
        this.dateStyle = StringUtils.isBlank(style) ? null : Style.valueOf(style.toUpperCase());
    }

    public void setTimeStyle(String style) {
        this.timeStyle = StringUtils.isBlank(style) ? null : Style.valueOf(style.toUpperCase());
    }

    public void setTimeFirst(boolean timeFirst)
    {
        this.timeFirst = timeFirst;
    }

    public void setSeparateDateTimeWith(String separate) {
        this.separateDateTimeWith = StringUtils.isBlank(separate) ? " " : separate;
    }

    public void setPattern(String pattern)
    {
        this.pattern = StringUtils.isBlank(pattern) ? null : pattern;
    }

    public void setTimeZone(Object timeZone) {
        if(timeZone instanceof String)
            this.timeZone = StringUtils.isBlank((String)timeZone) ? null :
                    TimeZone.getTimeZone((String)timeZone);
        else if(timeZone instanceof TimeZone)
            this.timeZone = (TimeZone)timeZone;
        else if(timeZone instanceof ZoneId)
            this.timeZone = TimeZone.getTimeZone((ZoneId)timeZone);
        else if(timeZone == null)
            this.timeZone = null;
        else
            throw new IllegalArgumentException("Time zone type " + timeZone.getClass().getCanonicalName() + " not recognized");
    }

    public void setVar(String var)
    {
        this.var = StringUtils.isBlank(var) ? null : var;
    }

    public void setScope(String scope)
    {
        this.scope = Util.getScope(scope);
    }

    private static enum Type {
        DATE, TIME, BOTH
    }

    private static enum Style {
        DEFAULT(DateFormat.MEDIUM, FormatStyle.MEDIUM),
        SHORT(DateFormat.SHORT, FormatStyle.SHORT),
        MEDIUM(DateFormat.MEDIUM, FormatStyle.MEDIUM),
        LONG(DateFormat.LONG, FormatStyle.LONG),
        FULL(DateFormat.FULL, FormatStyle.FULL);

        private final int dateFormat;

        private final FormatStyle formatStyle;

        private Style(int dateFormat, FormatStyle formatStyle) {
            this.dateFormat = dateFormat;
            this.formatStyle = formatStyle;
        }

        public int getDateFormat()
        {
            return dateFormat;
        }

        public FormatStyle getFormatStyle()
        {
            return formatStyle;
        }
    }
}
