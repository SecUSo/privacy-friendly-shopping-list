package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 16.05.16 15:06 creation date
 */
public abstract class AbstractEntity
{
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
