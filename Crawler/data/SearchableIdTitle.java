12
https://raw.githubusercontent.com/Pingvin235/bgerp/master/src/ru/bgcrm/model/SearchableIdTitle.java
package ru.bgcrm.model;

import java.util.ArrayList;
import java.util.List;

public class SearchableIdTitle extends IdTitle {
    private List<String> searchMode = new ArrayList<String>();

    public List<String> getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(List<String> searchMode) {
        this.searchMode = searchMode;
    }
}