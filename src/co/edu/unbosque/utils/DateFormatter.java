package co.edu.unbosque.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utility for formatting dates into complete Spanish text.
 * Converts ISO format dates (yyyy-MM-dd) to long text with timestamp of the registration moment.
 * Example: "2026-02-10" -> "Diez de Febrero de 2026 siendo las seis y veinte con treinta segundos de la tarde"
 */
public class DateFormatter {
    
    private static final String[] DAYS = {
        "", "Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho", "Nueve", "Diez",
        "Once", "Doce", "Trece", "Catorce", "Quince", "Dieciséis", "Diecisiete", "Dieciocho", 
        "Diecinueve", "Veinte", "Veintiuno", "Veintidós", "Veintitrés", "Veinticuatro", "Veinticinco", 
        "Veintiséis", "Veintisiete", "Veintiocho", "Veintinueve", "Treinta", "Treinta y uno"
    };
    
    private static final String[] MONTHS = {
        "", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    
    private static final String[] HOURS = {
        "doce", "una", "dos", "tres", "cuatro", "cinco", "seis", 
        "siete", "ocho", "nueve", "diez", "once", "doce"
    };
    
    private static final String[] MINUTES = {
        "en punto", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez",
        "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve",
        "veinte", "veintiuno", "veintidós", "veintitrés", "veinticuatro", "veinticinco", "veintiséis",
        "veintisiete", "veintiocho", "veintinueve", "treinta", "treinta y uno", "treinta y dos",
        "treinta y tres", "treinta y cuatro", "treinta y cinco", "treinta y seis", "treinta y siete",
        "treinta y ocho", "treinta y nueve", "cuarenta", "cuarenta y uno", "cuarenta y dos",
        "cuarenta y tres", "cuarenta y cuatro", "cuarenta y cinco", "cuarenta y seis", "cuarenta y siete",
        "cuarenta y ocho", "cuarenta y nueve", "cincuenta", "cincuenta y uno", "cincuenta y dos",
        "cincuenta y tres", "cincuenta y cuatro", "cincuenta y cinco", "cincuenta y seis",
        "cincuenta y siete", "cincuenta y ocho", "cincuenta y nueve"
    };
    
    public static String formatDateToText(String dateString) {
        try {
            return formatDateTimeToText(LocalDate.parse(dateString), LocalDateTime.now());
        } catch (Exception e) {
            return "Fecha inválida";
        }
    }
    
    public static String formatDateToText(LocalDate date) {
        return formatDateTimeToText(date, LocalDateTime.now());
    }
    
    public static String formatDateTimeToText(LocalDateTime dateTime) {
        return formatDateTimeToText(dateTime.toLocalDate(), dateTime);
    }
    
    private static String formatDateTimeToText(LocalDate date, LocalDateTime time) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(DAYS[date.getDayOfMonth()]);
        sb.append(" de ").append(MONTHS[date.getMonthValue()]);
        sb.append(" de ").append(date.getYear());
        
        int hour24 = time.getHour();
        int hour12 = hour24 % 12;
        String period = hour24 < 12 ? "de la mañana" : (hour24 < 20 ? "de la tarde" : "de la noche");
        
        sb.append(" siendo las ").append(HOURS[hour12]);
        
        int minutes = time.getMinute();

        if (minutes == 0) {
            sb.append(" ").append(MINUTES[0]);
        } else {
            sb.append(" y ").append(MINUTES[minutes]);
        }
        
        int seconds = time.getSecond();
        sb.append(" con ").append(numberToText(seconds)).append(" segundo");
        if (seconds != 1) sb.append("s");
        sb.append(" ").append(period);
        
        return sb.toString();
    }
    
    private static String numberToText(int n) {
        if (n == 0) return "cero";
        if (n < MINUTES.length) return MINUTES[n];
        return String.valueOf(n);
    }
    
    public static String getCurrentDateTimeAsText() {
        return formatDateTimeToText(LocalDateTime.now());
    }
    
    public static String convertDateForFileStorage(String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString);
            return convertDateForFileStorage(date);
        } catch (Exception e) {
            return "Fecha inválida";
        }
    }
    
    public static String convertDateForFileStorage(LocalDate date) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return formatDateTimeToText(date, currentDateTime);
    }
    
    public static String convertDateWithCurrentTimestamp(String dateString) {
        return convertDateForFileStorage(dateString);
    }
}
