package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 29.06.16 creation date
 */
public class DataAsStringTestCaseBuilder
{
    String inputDate;
    String inputPattern;
    String outputPaterrn;
    String language;
    String expectedDate;

    public String getInputDate()
    {
        return inputDate;
    }

    public DataAsStringTestCaseBuilder setInputDate(String inputDate)
    {
        this.inputDate = inputDate;
        return this;
    }

    public String getInputPattern()
    {
        return inputPattern;
    }

    public DataAsStringTestCaseBuilder setInputPattern(String inputPattern)
    {
        this.inputPattern = inputPattern;
        return this;
    }

    public String getOutputPaterrn()
    {
        return outputPaterrn;
    }

    public DataAsStringTestCaseBuilder setOutputPaterrn(String outputPaterrn)
    {
        this.outputPaterrn = outputPaterrn;
        return this;
    }

    public String getLanguage()
    {
        return language;
    }

    public DataAsStringTestCaseBuilder setLanguage(String language)
    {
        this.language = language;
        return this;
    }

    public String getExpectedDate()
    {
        return expectedDate;
    }

    public DataAsStringTestCaseBuilder setExpectedDate(String expectedDate)
    {
        this.expectedDate = expectedDate;
        return this;
    }
}
