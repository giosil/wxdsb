package org.dew.xds;

public 
class AffinityDomainIT implements IAffinityDomain 
{
	public String getClassDisplayName(String code) {
		if(code == null || code.length() == 0) return "";
		if(code.equals("CON")) return "Documento di consenso";
		if(code.equals("WOR")) return "Documento di workflow";
		if(code.equals("REF")) return "Referto";
		if(code.equals("LDO")) return "Lettera di dimissione ospedaliera";
		if(code.equals("RIC")) return "Richiesta";
		if(code.equals("SUM")) return "Sommario";
		if(code.equals("TAC")) return "Taccuino";
		return code;
	}
	
	public String getConfidentialityDisplayName(String code) {
		if(code == null || code.length() == 0) return "";
		if(code.equals("U")) return "Unrestricted";
		if(code.equals("L")) return "Low";
		if(code.equals("M")) return "Moderate";
		if(code.equals("N")) return "Normal";
		if(code.equals("R")) return "Restricted";
		if(code.equals("V")) return "Very Restricted";
		return code;
	}
	
	public String getFormatDisplayName(String code) {
		if(code == null || code.length() == 0) return "";
		if(code.equals("2.16.840.1.113883.10.20.1"))        return "Documento CCD";
		if(code.equals("2.16.840.1.113883.2.9.10.1.2"))     return "Prescrizione";
		if(code.equals("1.3.6.1.4.1.19376.1.5.3.1.1.7"))    return "Documento di Consenso BPPC";
		if(code.equals("2.16.840.1.113883.2.9.10.1.1"))     return "Referto di Laboratorio";
		if(code.equals("2.16.840.1.113883.2.9.10.2.4.1.1")) return "Profilo Sanitario Sintetico";
		if(code.equals("2.16.840.1.113883.2.9.10.1.5"))     return "Lettera di Dimissione Ospedaliera";
		if(code.equals("PDF")) return "PDF";
		if(code.equals("TXT")) return "TXT";
		return code;
	}
	
	public String getTypeDisplayName(String code) {
		if(code == null || code.length() == 0) return "";
		if(code.equals("57833-6")) return "Prescrizione farmaceutica";
		if(code.equals("60591-5")) return "Profilo Sanitario Sintetico";
		if(code.equals("11502-2")) return "Referto di Laboratorio";
		if(code.equals("57829-4")) return "Prescrizione per prodotto o apparecchiature mediche";
		if(code.equals("34105-7")) return "Lettera di dimissione ospedaliera";
		if(code.equals("59258-4")) return "Verbale di pronto soccorso";
		if(code.equals("68604-8")) return "Referto radiologico";
		if(code.equals("11526-1")) return "Referto di anatomia patologica";
		if(code.equals("59284-0")) return "Documento dei consensi";
		return code;
	}
	
	public String getFacilityDisplayName(String code) {
		if(code == null || code.length() == 0) return "";
		if(code.startsWith("O")) return "Ospedale";
		if(code.startsWith("P")) return "Prevenzione";
		if(code.startsWith("T")) return "Territorio";
		return code;
	}
	
	public String getPracticeDisplayName(String code) {
		if(code == null || code.length() == 0) return "";
		if(code.equals("AD_PSC001")) return "Allergologia";
		if(code.equals("AD_PSC002")) return "Day Hospital";
		if(code.equals("AD_PSC003")) return "Anatomia e Istologia Patologica";
		if(code.equals("AD_PSC005")) return "Angiologia";
		if(code.equals("AD_PSC006")) return "Cardiochirurgia Pediatrica";
		if(code.equals("AD_PSC007")) return "Cardiochirurgia";
		if(code.equals("AD_PSC008")) return "Cardiologia";
		if(code.equals("AD_PSC009")) return "Chirurgia Generale";
		if(code.equals("AD_PSC010")) return "Chirurgia Maxilofacciale";
		if(code.equals("AD_PSC011")) return "Chirurgia Pediatrica";
		if(code.equals("AD_PSC012")) return "Chirurgia Plastica";
		if(code.equals("AD_PSC013")) return "Chirurgia Toracica";
		if(code.equals("AD_PSC014")) return "Chirurgia Vascolare";
		if(code.equals("AD_PSC015")) return "Medicina Sportiva";
		if(code.equals("AD_PSC018")) return "Ematologia e Immunoematologia";
		if(code.equals("AD_PSC019")) return "Malattie Endocrine, del Ricambio e della Nutrizione";
		if(code.equals("AD_PSC020")) return "Immunologia";
		if(code.equals("AD_PSC021")) return "Geriatria";
		if(code.equals("AD_PSC024")) return "Malattie Infettive e Tropicali";
		if(code.equals("AD_PSC025")) return "Medicina del Lavoro";
		if(code.equals("AD_PSC026")) return "Medicina Generale";
		if(code.equals("AD_PSC028")) return "Unita Spinale";
		if(code.equals("AD_PSC029")) return "Nefrologia";
		if(code.equals("AD_PSC030")) return "Neurochirurgia";
		if(code.equals("AD_PSC031")) return "Nido";
		if(code.equals("AD_PSC032")) return "Neurologia";
		if(code.equals("AD_PSC033")) return "Neuropsichiatria Ingantile";
		if(code.equals("AD_PSC034")) return "Oculistica";
		if(code.equals("AD_PSC035")) return "Odontoiatria e Stomatologia";
		if(code.equals("AD_PSC036")) return "Ortopedia e Traumatologia";
		if(code.equals("AD_PSC037")) return "Ostetricia e Ginecologia";
		if(code.equals("AD_PSC038")) return "Otorinolaringoiatria";
		if(code.equals("AD_PSC039")) return "Pediatria";
		if(code.equals("AD_PSC040")) return "Psichiatria";
		if(code.equals("AD_PSC042")) return "Tossicologia";
		if(code.equals("AD_PSC043")) return "Urologia";
		if(code.equals("AD_PSC046")) return "Grandi Ustioni Pediatriche";
		if(code.equals("AD_PSC047")) return "Grandi Ustionati";
		if(code.equals("AD_PSC048")) return "Nefrologia (Abilitazione Trapianto Rene)";
		if(code.equals("AD_PSC049")) return "Terapia Intensiva";
		if(code.equals("AD_PSC050")) return "Unita Coronarica";
		if(code.equals("AD_PSC051")) return "Astanteria";
		if(code.equals("AD_PSC052")) return "Dermatologia";
		if(code.equals("AD_PSC054")) return "Emodialisi";
		if(code.equals("AD_PSC055")) return "Farmacologia Clinica";
		if(code.equals("AD_PSC056")) return "Recupero e Riabilitazione Funzionale";
		if(code.equals("AD_PSC057")) return "Fisiopatologia della Riabilitazione Umana";
		if(code.equals("AD_PSC058")) return "Gastroenterologia";
		if(code.equals("AD_PSC060")) return "Lungodegenti";
		if(code.equals("AD_PSC061")) return "Medicina Nucleare";
		if(code.equals("AD_PSC062")) return "Neonatologia";
		if(code.equals("AD_PSC064")) return "Oncologia";
		if(code.equals("AD_PSC065")) return "Oncoematologia Pediatrica";
		if(code.equals("AD_PSC066")) return "Oncoematologia";
		if(code.equals("AD_PSC068")) return "Pneumologia, Fisiopatologia Respiratoria, Tosiologia";
		if(code.equals("AD_PSC069")) return "Radiologia";
		if(code.equals("AD_PSC070")) return "Radioterapia";
		if(code.equals("AD_PSC071")) return "Reumatologia";
		if(code.equals("AD_PSC073")) return "Terapia Intensiva Neonatale";
		if(code.equals("AD_PSC074")) return "Radioterapia Oncologica";
		if(code.equals("AD_PSC075")) return "Neuro-Riabilitazione";
		if(code.equals("AD_PSC076")) return "Neurochirurgia Pediatrica";
		if(code.equals("AD_PSC077")) return "Nefrologia Pediatrica";
		if(code.equals("AD_PSC078")) return "Urologia Pediatrica";
		if(code.equals("AD_PSC082")) return "Anestesia e Rianimazione";
		if(code.equals("AD_PSC097")) return "Detenuti";
		if(code.equals("AD_PSC098")) return "Day Surgery Plurispecialistica";
		if(code.equals("AD_PSC100")) return "Laboratorio Analisi Chimico Cliniche";
		if(code.equals("AD_PSC101")) return "Microbiologia e Virologia";
		if(code.equals("AD_PSC102")) return "Centro Trasfusionale e Immunoematologico";
		if(code.equals("AD_PSC103")) return "Radiodiagnostica";
		if(code.equals("AD_PSC104")) return "Neuroradiologia";
		if(code.equals("AD_PSC106")) return "Pronto Soccorso e OBI";
		if(code.equals("AD_PSC107")) return "Poliambulatorio";
		if(code.equals("AD_PSC109")) return "Centrale Operativa 118";
		if(code.equals("AD_PSC121")) return "Comparti Operatori - Degenza Ordinaria";
		if(code.equals("AD_PSC122")) return "Comparti Operatori - Day Surgery";
		if(code.equals("AD_PSC126")) return "Libera Professione Degenza";
		if(code.equals("AD_PSC127")) return "Hospice Ospedaliero";
		if(code.equals("AD_PSC129")) return "Trapianto Organi e Tessuti";
		if(code.equals("AD_PSC130")) return "Medicina di Base";
		if(code.equals("AD_PSC131")) return "Assistenza Territoriale";
		if(code.equals("AD_PSC199")) return "Raccolta Consenso";
		return code;
	}
	
	public String getContentTypeDisplayName(String code) {
		if(code == null || code.length() == 0) return "";
		if(code.equalsIgnoreCase("PHR")) return "Personal Health Record Update";
		if(code.equalsIgnoreCase("CON")) return "Consulto";
		if(code.equalsIgnoreCase("DIS")) return "Discharge";
		if(code.equalsIgnoreCase("ERP")) return "Erogazione Prestazione Prenotata";
		return code;
	}
}
