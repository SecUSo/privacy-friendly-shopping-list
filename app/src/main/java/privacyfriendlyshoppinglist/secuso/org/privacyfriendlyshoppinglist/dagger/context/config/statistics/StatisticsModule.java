package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.dagger.context.config.statistics;

import dagger.Module;
import dagger.Provides;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.StatisticsService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.StatisticsServiceImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter.StatisticsConverterService;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.business.impl.converter.impl.StatisticsConverterServiceImpl;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.StatisticsDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.impl.StatisticsDaoImpl;

import javax.inject.Singleton;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
@Module(
        injects = {
                StatisticsDao.class,
                StatisticsService.class,
                StatisticsConverterService.class
        }
)
public class StatisticsModule
{
    @Singleton
    @Provides
    StatisticsDao provideStatisticsDao()
    {
        return new StatisticsDaoImpl();
    }

    @Singleton
    @Provides
    StatisticsConverterService provideStatisticsConverter()
    {
        return new StatisticsConverterServiceImpl();
    }

    @Singleton
    @Provides
    StatisticsService provideStatisticsService(
            StatisticsDao statisticsDao,
            StatisticsConverterService converterService
    )
    {
        return new StatisticsServiceImpl(statisticsDao, converterService);
    }

}
