package idv.laborLab.sharedLibrary.tools.request;

import java.util.Map;

public interface RequestRecordable {

    String getClientIP();
    Map<String, String> getRequestHeadersInMap();
}
