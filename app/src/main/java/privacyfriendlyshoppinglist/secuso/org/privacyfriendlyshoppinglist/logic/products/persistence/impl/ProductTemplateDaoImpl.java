package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.impl;

import android.content.Context;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.AbstractDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence.DB;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.ProductTemplateDao;
import privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.logic.products.persistence.entity.ProductTemplateEntity;

import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 09.06.16 creation date
 */
public class ProductTemplateDaoImpl extends AbstractDao<ProductTemplateEntity> implements ProductTemplateDao
{
    @Override
    public Long save(ProductTemplateEntity entity)
    {
        return saveOrUpdate(entity);
    }

    @Override
    public ProductTemplateEntity getById(Long id)
    {
        return getById(id, ProductTemplateEntity.class);
    }

    @Override
    public List<ProductTemplateEntity> getAllEntities()
    {
        return getAllEntities(ProductTemplateEntity.class);
    }

    @Override
    public Boolean deleteById(Long id)
    {
        return deleteById(id, ProductTemplateEntity.class);
    }
}
