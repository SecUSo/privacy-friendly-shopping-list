package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.impl;

import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.StatisticsDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.statistics.persistence.entity.StatisticEntryEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 23.07.16 creation date
 */
public class StatisticsDaoImpl extends AbstractDao<StatisticEntryEntity> implements StatisticsDao
{
    @Override
    public Long save(StatisticEntryEntity entity)
    {
        return saveOrUpdate(entity);
    }

    @Override
    public StatisticEntryEntity getById(Long id)
    {
        return getById(id, StatisticEntryEntity.class);
    }

    @Override
    public List<StatisticEntryEntity> getAllEntities()
    {
        return getAllEntities(StatisticEntryEntity.class);
    }

    @Override
    public Boolean deleteById(Long id)
    {
        return deleteById(id, StatisticEntryEntity.class);
    }
}
