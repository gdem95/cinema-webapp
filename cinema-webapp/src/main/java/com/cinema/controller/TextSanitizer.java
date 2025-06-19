package com.cinema.controller;

public class TextSanitizer {
	
    private TextSanitizer() {}
    
    //Rimuove tag HTML e script dal testo per sanitizzare l'input.
    public static String sanitize(String input) {
    	
        if (input == null) {
            return null;
        }
        
        String cleaned = input.replaceAll("<.*?>", "");
        
        cleaned = cleaned.replaceAll("(?i)<script.*?>.*?</script>", "");
        return cleaned.trim();
    }
}
