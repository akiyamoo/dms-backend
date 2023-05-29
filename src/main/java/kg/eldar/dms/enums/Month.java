package kg.eldar.dms.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Month {
    JAN("Январь", 1),
    FEB("Февраль", 2),
    MAR("Март", 3),
    APR("Апрель", 4),
    MAY("Май", 5),
    JUN("Июнь", 6),
    JUL("Июль", 7),
    AUG("Август", 8),
    SEP("Сентябрь", 9),
    OKT("Октябрь", 10),
    NOV("Ноябрь", 11),
    DEC("Декабрь", 12);

    final String rus;
    final int month;

    public static Month getMonth(String name) {
        return Arrays.stream(values()).filter(m -> m.rus.equals(name)).findFirst().orElse(null);
    }
}
