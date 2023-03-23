package idv.laborLab.sharedLibrary.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level {

    INFO,       // not write into record-service db
    WARNING,    // write into db
    ERROR;      // write into db and send email notification
}
