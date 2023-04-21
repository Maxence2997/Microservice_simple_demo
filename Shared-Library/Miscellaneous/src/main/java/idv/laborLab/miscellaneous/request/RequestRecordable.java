package idv.laborLab.miscellaneous.request;

import java.util.Map;

public interface RequestRecordable {

    String getClientIP();
    Map<String, String> getRequestHeadersInMap();
}
