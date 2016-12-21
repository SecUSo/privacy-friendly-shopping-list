package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.utils;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 17.07.16 creation date
 */
@RunWith(JUnitParamsRunner.class)
public class DateUtilsTest
{
    private static final String DE_PATTERN = "EE dd.MM.yyyy HH:mm";
    private static final String US_PATTERN = "EE MM/dd/yyyy HH:mm";
    private static final String JA_PATTERN = "yyyy/MM/dd EE HH:mm";

    private DataAsStringTestCaseBuilder[] parametersForFormattedDateString()
    {
        return new DataAsStringTestCaseBuilder[]{
                new DataAsStringTestCaseBuilder()
                        .setInputDate("2016-06-29 15:37")
                        .setInputPattern(DateUtils.ISO_PATTERN_MIN)
                        .setOutputPaterrn(US_PATTERN)
                        .setLanguage(DateUtils.US)
                        .setExpectedDate("Wed 06/29/2016 15:37"),

                new DataAsStringTestCaseBuilder()
                        .setInputDate("2016-06-29 15:37")
                        .setInputPattern(DateUtils.ISO_PATTERN_MIN)
                        .setOutputPaterrn(DE_PATTERN)
                        .setLanguage(DateUtils.DE)
                        .setExpectedDate("Mi 29.06.2016 15:37"),

                new DataAsStringTestCaseBuilder()
                        .setInputDate("2016-06-29 15:37")
                        .setInputPattern(DateUtils.ISO_PATTERN_MIN)
                        .setOutputPaterrn(JA_PATTERN)
                        .setLanguage(DateUtils.JA)
                        .setExpectedDate("2016/06/29 êÖ 15:37"),

                new DataAsStringTestCaseBuilder()
                        .setInputDate("Wed 06/29/2016 15:37")
                        .setInputPattern(US_PATTERN)
                        .setOutputPaterrn(DateUtils.ISO_PATTERN_MIN)
                        .setLanguage(DateUtils.US)
                        .setExpectedDate("2016-06-29 15:37"),

                new DataAsStringTestCaseBuilder()
                        .setInputDate("Mi 29.06.2016 15:37")
                        .setInputPattern(DE_PATTERN)
                        .setOutputPaterrn(DateUtils.ISO_PATTERN_MIN)
                        .setLanguage(DateUtils.DE)
                        .setExpectedDate("2016-06-29 15:37"),

                new DataAsStringTestCaseBuilder()
                        .setInputDate("2016/06/29 êÖ 15:37")
                        .setInputPattern(JA_PATTERN)
                        .setOutputPaterrn(DateUtils.ISO_PATTERN_MIN)
                        .setLanguage(DateUtils.JA)
                        .setExpectedDate("2016-06-29 15:37"),
        };
    }

    @Test
    @Parameters(method = "parametersForFormattedDateString")
    public void getFormattedDateString(DataAsStringTestCaseBuilder data) throws Exception
    {
        String formattedDateString = DateUtils.getFormattedDateString(
                data.getInputDate(),
                data.getInputPattern(),
                data.getOutputPaterrn(),
                data.getLanguage());

        assertEquals(data.getExpectedDate(), formattedDateString);
    }
}