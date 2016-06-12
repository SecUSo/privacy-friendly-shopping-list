package privacyfriendlyshoppinglist.secuso.org.privacyfriendlyshoppinglist.framework.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author: Grebiel Jose Ifill Brito
 * Created: 11.06.16 creation date
 */
public abstract class AbstractDto
{
    private String id;
    private List<String> validationErrors = new ArrayList<>();

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<String> getValidationErrorsList()
    {
        return validationErrors;
    }

    public boolean hasErrors()
    {
        return validationErrors.size() > 0;
    }
}
