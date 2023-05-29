package kg.eldar.dms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Result {
    DELETE(""),
    NOT_DELETE("Запись не была удалена. Произошла ошибка!"),
    UPDATE(""),
    NOT_UPDATE("Запись не была обновлена. Произошла ошибка!"),
    INSERT(""),
    NOT_INSERT("Запись не была создана. Произошла ошибка!");

    final String description;
}
