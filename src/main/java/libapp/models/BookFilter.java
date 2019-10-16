package libapp.models;

public class BookFilter
{
    private String searchString;

    private String[] selectedTags;

    public String getSearchString()
    {
        return searchString;
    }

    public void setSearchString(String searchString)
    {
        this.searchString = searchString;
    }

    public String[] getSelectedTags()
    {
        return selectedTags;
    }

    public void setSelectedTags(String[] selectedTags)
    {
        this.selectedTags = selectedTags;
    }
}
