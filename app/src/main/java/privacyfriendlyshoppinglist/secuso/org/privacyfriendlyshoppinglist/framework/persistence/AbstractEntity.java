package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.persistence;

import com.j256.ormlite.field.DatabaseField;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:06 creation date
 */
public abstract class AbstractEntity
{
    @DatabaseField(generatedId = true)
    private Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}

