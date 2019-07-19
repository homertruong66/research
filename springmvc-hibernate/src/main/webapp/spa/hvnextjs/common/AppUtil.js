function formatParams(formatString, params) {
	formatted = formatString
	for(i in params) {
        formatted = formatted.replace("{" + i + "}", params[i]);
    }
    return formatted;
}