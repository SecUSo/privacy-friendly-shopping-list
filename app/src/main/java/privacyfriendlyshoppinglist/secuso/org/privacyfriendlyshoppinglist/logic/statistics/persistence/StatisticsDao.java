package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.context.ContextSetter;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public interface StatisticsDao extends ContextSetter
{
    Long save(StatisticEntryEntity entity);

    StatisticEntryEntity getById(Long id);

    List<StatisticEntryEntity> getAllEntities();

    Boolean deleteById(Long id);
}
