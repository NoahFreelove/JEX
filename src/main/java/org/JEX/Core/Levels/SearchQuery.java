package org.JEX.Core.Levels;

import org.JEX.Core.Engine.GameObject;

public class SearchQuery {
    public boolean found = false;
    public GameObject object = null;
    public String name = null;
    public String tag = null;
    public SearchType type;
    public int tag_id;

    public enum SearchType{
        Name,
        Tag,
        NameTag
    }
}
