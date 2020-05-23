package br.edu.ifpb.mestrado.openplanner.api.infrastructure.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import br.edu.ifpb.mestrado.openplanner.api.domain.shared.RecorrenciaTimeUnit;

public class DateUtils {

    public static LocalDateTime plusOne(LocalDateTime dataHora, RecorrenciaTimeUnit timeUnit) {
        Map<RecorrenciaTimeUnit, ChronoUnit> unitMap = Map.of(
                RecorrenciaTimeUnit.DAY, ChronoUnit.DAYS,
                RecorrenciaTimeUnit.WEEK, ChronoUnit.WEEKS,
                RecorrenciaTimeUnit.MONTH, ChronoUnit.MONTHS,
                RecorrenciaTimeUnit.YEAR, ChronoUnit.YEARS);

        return dataHora.plus(1, unitMap.get(timeUnit));
    }

}
