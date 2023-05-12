package org.dew.xds;

public 
class AffinityDomainIT implements IAffinityDomain 
{
  public static final String sROOT_OID_HL7_IT            = "2.16.840.1.113883.2.9.2.";
  public static final String sROOT_OID_TAXCODE           = "2.16.840.1.113883.2.9.4.3.2";
  public static final String sROOT_OID_CONFIDENTIALITY   = "2.16.840.1.113883.5.25";
  public static final String sROOT_OID_TYPE_CODE         = "2.16.840.1.113883.6.1";
  public static final String sROOT_OID_ICD9_CM           = "2.16.840.1.113883.6.2";
  public static final String sROOT_OID_ICD10             = "2.16.840.1.113883.6.3";
  public static final String sROOT_OID_SNOMED            = "2.16.840.1.113883.6.5";
  public static final String sROOT_OID_SNOMED_CT         = "2.16.840.1.113883.6.96";
  public static final String sROOT_OID_ICD9_DIAGNOSIS    = "2.16.840.1.113883.6.103";
  public static final String sROOT_OID_ICD9_PROCEDURES   = "2.16.840.1.113883.6.104";
  
  public static final String sDOC_PRESCRIZIONE_FARM      = "57833-6";
  public static final String sDOC_PRESCRIZIONE_FARM_OBS  = "29305-0"; // Obsoleto
  public static final String sDOC_PROFILO_SANITARIO_SIN  = "60591-5";
  public static final String sDOC_REFERTO_LABORATORIO    = "11502-2";
  public static final String sDOC_PRESCRIZIONE_APPAR_MED = "57829-4";
  public static final String sDOC_LETTERA_DIM_OSP        = "34105-7";
  public static final String sDOC_VERBALE_PRONTO_SOCC    = "59258-4";
  public static final String sDOC_REFERTO_RADIOLOGIA     = "68604-8";
  public static final String sDOC_REFERTO_ANATOMIA_PAT   = "11526-1";
  public static final String sDOC_REGISTRAZIONE_CONSENSO = "59284-0";
  public static final String sDOC_CERTIFICATO_MALATTIA   = "28653-4";
  public static final String sDOC_PRESCRIZIONE_SPEC      = "57832-8";
  public static final String sDOC_EROGAZIONE_FARM        = "29304-3";
  public static final String sDOC_REFERTO_SPECIALISTICO  = "11488-4";
  public static final String sDOC_ESENZIONE_DA_REDDITO   = "57827-8";
  public static final String sDOC_REFERTO_AMBULATORIALE  = "83798-9";
  public static final String sDOC_EROGAZIONE_SPEC        = "81223-0";
  public static final String sDOC_PROMEMORIA_PREN_CUP    = "86530-3";
  public static final String sDOC_ANNULLAMENTO           = "11506-3";
  public static final String sDOC_PDTA                   = "60591-6";
  public static final String sDOC_REFERTO_GENERICO       = "47045-0";
  public static final String sDOC_EMERGENCY_DATA_SET     = "60592-3";
  public static final String sDOC_RICHIESTA_DI_RICOVERO  = "57830-2";
  public static final String sDOC_RICHIESTA_TRASPORTO    = "57834-4";
  public static final String sDOC_VACCINAZIONI           = "11369-6";
  public static final String sDOC_CERTIFICATO_VACCINALE  = "82593-5";
  public static final String sDOC_SCHEDA_VACCINALE       = "87273-9";
  public static final String sDOC_DIGITAL_GREEN_CERT     = "97500-3";
  public static final String sDOC_CERTIFICATO_GUARIGIONE = "97499-8";
  public static final String sDOC_RESOCONTO_SICUREZZA    = "55750-4";
  public static final String sDOC_BILANCIO_DI_SALUTE     = "68814-3";
  
  @Override
  public String getClassDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0) return defaultValue;
    if(code.equals("CON")) return "Documento di consenso";
    if(code.equals("WOR")) return "Documento di workflow";
    if(code.equals("REF")) return "Referto";
    if(code.equals("LDO")) return "Lettera di dimissione ospedaliera";
    if(code.equals("RIC")) return "Richiesta";
    if(code.equals("SUM")) return "Sommario";
    if(code.equals("TAC")) return "Taccuino";
    if(code.equals("PRS")) return "Prescrizione";
    if(code.equals("PRE")) return "Prestazioni";
    if(code.equals("ESE")) return "Esenzione";
    if(code.equals("PDC")) return "Piano di cura";
    if(code.equals("VAC")) return "Vaccino";
    if(code.equals("CER")) return "Certificato";
    if(code.equals("VRB")) return "Verbale";
    if(code.equals("CNT")) return "Documento di controllo";
    return defaultValue;
  }
  
  @Override
  public String getConfidentialityDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0) return defaultValue;
    if(code.equals("U")) return "Unrestricted";
    if(code.equals("L")) return "Low";
    if(code.equals("M")) return "Moderate";
    if(code.equals("N")) return "Normal";           // *
    if(code.equals("R")) return "Restricted";
    if(code.equals("V")) return "Very Restricted";  // *
    return defaultValue;
  }
  
  @Override
  public String getFormatDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0) return defaultValue;
    if(code.equals("2.16.840.1.113883.10.20.1"))         return "Documento CCD";
    if(code.equals("2.16.840.1.113883.2.9.10.1.2"))      return "Prescrizione";
    if(code.equals("1.3.6.1.4.1.19376.1.5.3.1.1.7"))     return "Documento di Consenso BPPC";
    if(code.equals("2.16.840.1.113883.2.9.10.1.1"))      return "Referto di Laboratorio";
    if(code.equals("2.16.840.1.113883.2.9.10.2.4.1.1"))  return "Profilo Sanitario Sintetico";
    if(code.equals("2.16.840.1.113883.2.9.10.1.5"))      return "Lettera di Dimissione Ospedaliera";
    if(code.equals("2.16.840.1.113883.2.9.10.1.7"))      return "Referto di Radiologia";
    if(code.equals("2.16.840.1.113883.2.9.4.3.14"))      return "Piano Terapeutico";
    if(code.equals("2.16.840.1.113883.2.9.10.1.11.1.1")) return "Scheda Vaccinale";
    if(code.equals("2.16.840.1.113883.2.9.10.1.11.1.2")) return "Certificato Vaccinale";
    if(code.equals("2.16.840.1.113883.2.9.10.1.6.1"))    return "Verbale di Pronto Soccorso";
    if(code.equals("2.16.840.1.113883.2.9.10.1.9.1"))    return "Referto di Specialistica Ambulatoriale";
    if(code.equals("SistemaTS-Prestazione"))             return "Prestazione";
    if(code.equals("SistemaTS-Prescrizione"))            return "Prescrizione";
    if(code.equals("SistemaTS-Esenzione"))               return "Esenzione";
    if(code.equals("PDF")) return "PDF";
    if(code.equals("TXT")) return "TXT";
    return defaultValue;
  }
  
  @Override
  public String getTypeDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0) return defaultValue;
    if(code.equals(sDOC_PRESCRIZIONE_FARM))      return "Prescrizione farmaceutica";
    if(code.equals(sDOC_PRESCRIZIONE_FARM_OBS))  return "Prescrizione farmaceutica";
    if(code.equals(sDOC_PROFILO_SANITARIO_SIN))  return "Profilo Sanitario Sintetico";
    if(code.equals(sDOC_REFERTO_LABORATORIO))    return "Referto di Laboratorio";
    if(code.equals(sDOC_PRESCRIZIONE_APPAR_MED)) return "Prescrizione apparecchiature medicali";
    if(code.equals(sDOC_LETTERA_DIM_OSP))        return "Lettera di dimissione ospedaliera";
    if(code.equals(sDOC_VERBALE_PRONTO_SOCC))    return "Referto di Pronto Soccorso";
    if(code.equals(sDOC_REFERTO_RADIOLOGIA))     return "Referto di Radiologia";
    if(code.equals(sDOC_REFERTO_ANATOMIA_PAT))   return "Referto di Anatomia Patologica";
    if(code.equals(sDOC_REGISTRAZIONE_CONSENSO)) return "Registrazione consenso";
    if(code.equals(sDOC_CERTIFICATO_MALATTIA))   return "Certificato di malattia";
    if(code.equals(sDOC_PRESCRIZIONE_SPEC))      return "Prescrizione specialistica";
    if(code.equals(sDOC_EROGAZIONE_FARM))        return "Erogazione farmaceutica";
    if(code.equals(sDOC_REFERTO_SPECIALISTICO))  return "Referto specialistico";
    if(code.equals(sDOC_ESENZIONE_DA_REDDITO))   return "Esenzione da reddito";
    if(code.equals(sDOC_REFERTO_AMBULATORIALE))  return "Referto ambulatoriale";
    if(code.equals(sDOC_EROGAZIONE_SPEC))        return "Erogazione specialistica";
    if(code.equals(sDOC_PROMEMORIA_PREN_CUP))    return "Promemoria di prenotazione";
    if(code.equals(sDOC_ANNULLAMENTO))           return "Annullamento";
    if(code.equals(sDOC_PDTA))                   return "PDTA";
    if(code.equals(sDOC_REFERTO_GENERICO))       return "Referto Generico";
    if(code.equals(sDOC_EMERGENCY_DATA_SET))     return "Emergency Data Set";
    if(code.equals(sDOC_RICHIESTA_DI_RICOVERO))  return "Richiesta di ricovero";
    if(code.equals(sDOC_RICHIESTA_TRASPORTO))    return "Richiesta di trasporto";
    if(code.equals(sDOC_VACCINAZIONI))           return "Vaccinazioni";
    if(code.equals(sDOC_CERTIFICATO_VACCINALE))  return "Certificato Vaccinale";
    if(code.equals(sDOC_SCHEDA_VACCINALE))       return "Scheda Vaccinale";
    if(code.equals(sDOC_DIGITAL_GREEN_CERT))     return "Certificato Digitale Covid-19";
    if(code.equals(sDOC_CERTIFICATO_GUARIGIONE)) return "Certificato di guarigione";
    if(code.equals(sDOC_RESOCONTO_SICUREZZA))    return "Resoconto sicurezza del paziente";
    if(code.equals(sDOC_BILANCIO_DI_SALUTE))     return "Bliancio di salute pediatrico";
    if(defaultValue != null && defaultValue.equalsIgnoreCase("document")) {
      return "Documento sanitario";
    }
    return defaultValue;
  }
  
  @Override
  public String getFacilityDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0) return defaultValue;
    if(defaultValue != null && defaultValue.equals("!")) {
      if(code.equals("Ospedale"))    return "Ospedale";
      if(code.equals("Prevenzione")) return "Prevenzione";
      if(code.equals("Territorio"))  return "Territorio";
      if(code.equals("SistemaTS"))   return "SistemaTS";
      if(code.equals("Cittadino"))   return "Cittadino";
      if(code.equals("MdsPN-DGC"))   return "MdsPN-DGC";
    }
    else {
      if(code.startsWith("O")) return "Ospedale";
      if(code.startsWith("P")) return "Prevenzione";
      if(code.startsWith("T")) return "Territorio";
      if(code.startsWith("S")) return "SistemaTS";
      if(code.startsWith("C")) return "Cittadino";
      if(code.startsWith("M")) return "MdsPN-DGC";
    }
    return defaultValue;
  }
  
  @Override
  public String getPracticeDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0) return defaultValue;
    if(code.equals("AD_PSC001")) return "Allergologia";
    if(code.equals("AD_PSC002")) return "Day Hospital";
    if(code.equals("AD_PSC003")) return "Anatomia e Istologia Patologica";
    if(code.equals("AD_PSC005")) return "Angiologia";
    if(code.equals("AD_PSC006")) return "Cardiochirurgia Pediatrica";
    if(code.equals("AD_PSC007")) return "Cardiochirurgia";
    if(code.equals("AD_PSC008")) return "Cardiologia";
    if(code.equals("AD_PSC009")) return "Chirurgia Generale";
    if(code.equals("AD_PSC010")) return "Chirurgia Maxillo-Facciale";
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
    if(code.equals("AD_PSC025")) return "Medicina del Lavoro ";
    if(code.equals("AD_PSC026")) return "Medicina Generale";
    if(code.equals("AD_PSC027")) return "Medicina Legale ";
    if(code.equals("AD_PSC028")) return "Unita Spinale";
    if(code.equals("AD_PSC029")) return "Nefrologia";
    if(code.equals("AD_PSC030")) return "Neurochirurgia";
    if(code.equals("AD_PSC031")) return "Nido";
    if(code.equals("AD_PSC032")) return "Neurologia";
    if(code.equals("AD_PSC033")) return "Neuropsichiatria Infantile";
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
    if(code.equals("AD_PSC050")) return "Unita' Coronarica";
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
    if(code.equals("AD_PSC068")) return "Pneumologia, Fisiopatologia Respiratoria, Tisiologia";
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
    if(code.equals("AD_PSC999")) return "Altro";
    return defaultValue;
  }
  
  @Override
  public String getContentTypeDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0) return defaultValue;
    if(code.equalsIgnoreCase("PHR"))       return "Personal Health Record Update";
    if(code.equalsIgnoreCase("CON"))       return "Consulto";
    if(code.equalsIgnoreCase("DIS"))       return "Discharge";
    if(code.equalsIgnoreCase("ERP"))       return "Erogazione Prestazione Prenotata";
    if(code.equalsIgnoreCase("SistemaTS")) return "Documenti Sistema TS";
    if(code.equalsIgnoreCase("INI"))       return "Documenti INI";
    if(code.equalsIgnoreCase("PN-DGC"))    return "Documenti PN-DGC";
    if(code.equalsIgnoreCase("OBS"))       return "Documento stato di salute";
    return defaultValue;
  }
  
  @Override
  public String getEventDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0)  return defaultValue;
    if(code.equalsIgnoreCase("P99"))        return "Oscuramento del documento";
    if(code.equalsIgnoreCase("P97"))        return "Oscuramento al genitore";
    if(code.equalsIgnoreCase("P98"))        return "Oscuramento all'assistito";
    if(code.equalsIgnoreCase("J07BX03"))    return "Vaccino per Covid-19";
    if(code.equalsIgnoreCase("LP418019-8")) return "Tampone antigenico per Covid-19";
    if(code.equalsIgnoreCase("LP417541-2")) return "Tampone molecolare per Covid-19";
    if(code.equalsIgnoreCase("96118-5"))    return "Test Sierologico qualitativo";
    if(code.equalsIgnoreCase("94503-0"))    return "Test Sierologico quantitativo";
    if(code.equalsIgnoreCase("pay"))        return "Prescrizione farmaceutica non a carico SSN";
    if(code.equalsIgnoreCase("PUBLICPOL"))  return "Prescrizione farmaceutica SSN";
    if(code.equalsIgnoreCase("LP267463-0")) return "Reddito";
    if(code.equalsIgnoreCase("LP199190-2")) return "Patologia";
    if(code.equalsIgnoreCase("90768-3"))    return "Analisi sangue donatore";
    return defaultValue;
  }
  
  @Override
  public String getAdministrativeRequestDisplayName(String code, String defaultValue) {
    if(defaultValue == null) defaultValue = "";
    if(code == null || code.length() == 0)  return defaultValue;
    if(code.equalsIgnoreCase("SSN"))        return "Regime SSN";
    if(code.equalsIgnoreCase("INPATIENT"))  return "Regime di ricovero";
    if(code.equalsIgnoreCase("NOSSN"))      return "Regime privato";
    if(code.equalsIgnoreCase("SSR"))        return "Regime SSR";
    if(code.equalsIgnoreCase("DONOR"))      return "Regime donatori";
    return defaultValue;
  }
  
  @Override
  public String getClassByType(String type, String defaultValue) {
    if(type == null || type.length() == 0) {
      return defaultValue;
    }
    if(sDOC_REFERTO_LABORATORIO.equals(type) || sDOC_REFERTO_ANATOMIA_PAT.equals(type) || sDOC_REFERTO_RADIOLOGIA.equals(type)) {
      return "REF";
    }
    if(sDOC_REFERTO_AMBULATORIALE.equals(type) || sDOC_REFERTO_SPECIALISTICO.equals(type) || sDOC_REFERTO_GENERICO.equals(type)) {
      return "REF";
    }
    if(sDOC_PRESCRIZIONE_FARM.equals(type) || sDOC_PRESCRIZIONE_FARM_OBS.equals(type)) {
      return "RIC";
    }
    if(sDOC_PRESCRIZIONE_SPEC.equals(type)) {
      return "RIC";
    }
    if(sDOC_PRESCRIZIONE_APPAR_MED.equals(type)) {
      return "RIC";
    }
    if(sDOC_REGISTRAZIONE_CONSENSO.equals(type)) {
      return "CON";
    }
    if(sDOC_PROFILO_SANITARIO_SIN.equals(type)) {
      return "SUM";
    }
    if(sDOC_VACCINAZIONI.equals(type)) {
      return "VAC";
    }
    if(sDOC_CERTIFICATO_VACCINALE.equals(type)) {
      return "VAC";
    }
    if(sDOC_SCHEDA_VACCINALE.equals(type)) {
      return "VAC";
    }
    if(sDOC_LETTERA_DIM_OSP.equals(type)) {
      return "LDO";
    }
    if(sDOC_PDTA.equals(type)) {
      return "WOR";
    }
    if(sDOC_DIGITAL_GREEN_CERT.equals(type)) {
      return "CER";
    }
    if(sDOC_VERBALE_PRONTO_SOCC.equals(type)) {
      return "VRB";
    }
    return defaultValue;
  }
  
  @Override
  public String getPracticeByType(String type, String defaultValue) {
    if(type == null || type.length() == 0) {
      return defaultValue;
    }
    if(sDOC_REFERTO_LABORATORIO.equals(type)) {
      return "AD_PSC100"; // Laboratorio Analisi Chimico Cliniche
    }
    if(sDOC_REFERTO_ANATOMIA_PAT.equals(type)) {
      return "AD_PSC003"; // Anatomia e Istologia Patologica
    }
    if(sDOC_REFERTO_RADIOLOGIA.equals(type)) {
      return "AD_PSC069"; // Radiologia
    }
    if(sDOC_REFERTO_AMBULATORIALE.equals(type)) {
      return "AD_PSC107"; // Poliambulatorio
    }
    if(sDOC_PRESCRIZIONE_FARM.equals(type) || sDOC_PRESCRIZIONE_FARM_OBS.equals(type)) {
      return "AD_PSC130"; // Medicina di Base
    }
    if(sDOC_PRESCRIZIONE_SPEC.equals(type)) {
      return "AD_PSC130"; // Medicina di Base
    }
    if(sDOC_PRESCRIZIONE_APPAR_MED.equals(type)) {
      return "AD_PSC130"; // Medicina di Base
    }
    if(sDOC_REGISTRAZIONE_CONSENSO.equals(type)) {
      return "AD_PSC199"; // Raccolta Consenso
    }
    if(sDOC_PROFILO_SANITARIO_SIN.equals(type)) {
      return "AD_PSC130"; // Medicina di Base
    }
    if(sDOC_VACCINAZIONI.equals(type)) {
      return "AD_PSC020"; // Immunologia
    }
    if(sDOC_CERTIFICATO_VACCINALE.equals(type)) {
      return "AD_PSC020"; // Immunologia
    }
    if(sDOC_SCHEDA_VACCINALE.equals(type)) {
      return "AD_PSC020"; // Immunologia
    }
    if(sDOC_LETTERA_DIM_OSP.equals(type)) {
      return "AD_PSC026"; // Medicina Generale
    }
    if(sDOC_VERBALE_PRONTO_SOCC.equals(type)) {
      return "AD_PSC106"; // Pronto Soccorso e OBI
    }
    if(sDOC_PDTA.equals(type)) {
      return "AD_PSC131"; // Assistenza Territoriale
    }
    return defaultValue;
  }
  
  @Override
  public String getFacilityByType(String type, String defaultValue) {
    if(type == null || type.length() == 0) {
      return defaultValue;
    }
    if(sDOC_PRESCRIZIONE_FARM.equals(type) || sDOC_PRESCRIZIONE_FARM_OBS.equals(type)) {
      return "Territorio";
    }
    if(sDOC_PRESCRIZIONE_SPEC.equals(type)) {
      return "Territorio";
    }
    if(sDOC_PRESCRIZIONE_APPAR_MED.equals(type)) {
      return "Territorio";
    }
    if(sDOC_PROFILO_SANITARIO_SIN.equals(type)) {
      return "Territorio";
    }
    if(sDOC_CERTIFICATO_VACCINALE.equals(type)) {
      return "Prevenzione";
    }
    if(sDOC_SCHEDA_VACCINALE.equals(type)) {
      return "Prevenzione";
    }
    return "Ospedale";
  }
  
  @Override
  public String getContentTypeByType(String type, String defaultValue) {
    if(type == null || type.length() == 0) {
      return defaultValue;
    }
    if(sDOC_LETTERA_DIM_OSP.equals(type)) {
      return "DIS";
    }
    return "ERP";
  }
  
  @Override
  public String getFormatByType(String type, String mimeType, String defaultValue) {
    if(mimeType == null || mimeType.length() == 0) {
      if(type == null || type.length() == 0) {
        return defaultValue;
      }
    }
    if(mimeType != null && mimeType.indexOf("pdf") >= 0) {
      return "PDF";
    }
    if(mimeType != null && mimeType.equals("text/plain")) {
      return "TXT";
    }
    if(sDOC_REFERTO_LABORATORIO.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.1";
    }
    if(sDOC_REFERTO_RADIOLOGIA.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.7";
    }
    if(sDOC_PRESCRIZIONE_FARM.equals(type) || sDOC_PRESCRIZIONE_FARM_OBS.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.2";
    }
    if(sDOC_PRESCRIZIONE_SPEC.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.2";
    }
    if(sDOC_PRESCRIZIONE_APPAR_MED.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.2";
    }
    if(sDOC_REGISTRAZIONE_CONSENSO.equals(type)) {
      return "1.3.6.1.4.1.19376.1.5.3.1.1.7";
    }
    if(sDOC_PROFILO_SANITARIO_SIN.equals(type)) {
      return "2.16.840.1.113883.2.9.10.2.4.1.1";
    }
    if(sDOC_LETTERA_DIM_OSP.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.5";
    }
    if(sDOC_SCHEDA_VACCINALE.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.11.1.1";
    }
    if(sDOC_CERTIFICATO_VACCINALE.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.11.1.2";
    }
    if(sDOC_VERBALE_PRONTO_SOCC.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.6.1";
    }
    if(sDOC_REFERTO_AMBULATORIALE.equals(type) || sDOC_REFERTO_SPECIALISTICO.equals(type) || sDOC_REFERTO_GENERICO.equals(type)) {
      return "2.16.840.1.113883.2.9.10.1.9.1";
    }
    return defaultValue;
  }
  
  @Override
  public String getTemplateIdRoot(String code) {
    if(code == null || code.length() == 0) {
      return "";
    }
    if(code.equals(sDOC_PRESCRIZIONE_FARM))      return "2.16.840.1.113883.2.9.10.2.6";
    if(code.equals(sDOC_PRESCRIZIONE_FARM_OBS))  return "2.16.840.1.113883.2.9.10.2.6";
    if(code.equals(sDOC_PROFILO_SANITARIO_SIN))  return "2.16.840.1.113883.2.9.10.2.4";
    if(code.equals(sDOC_REFERTO_LABORATORIO))    return "2.16.840.1.113883.2.9.10.2.16";
    if(code.equals(sDOC_PRESCRIZIONE_APPAR_MED)) return "2.16.840.1.113883.2.9.10.2.7";
    if(code.equals(sDOC_LETTERA_DIM_OSP))        return "2.16.840.1.113883.2.9.10.2.10.99";
    if(code.equals(sDOC_VERBALE_PRONTO_SOCC))    return "2.16.840.1.113883.2.9.10.1.6.1";
    if(code.equals(sDOC_REFERTO_RADIOLOGIA))     return "2.16.840.1.113883.2.9.10.2.16";
    if(code.equals(sDOC_REFERTO_ANATOMIA_PAT))   return "2.16.840.1.113883.2.9.10.2.16";
    if(code.equals(sDOC_REGISTRAZIONE_CONSENSO)) return "2.16.840.1.113883.2.9.10.2.27";
    if(code.equals(sDOC_CERTIFICATO_MALATTIA))   return "2.16.840.1.113883.2.9.10.2.4";
    if(code.equals(sDOC_PRESCRIZIONE_SPEC))      return "2.16.840.1.113883.2.9.10.2.7";
    if(code.equals(sDOC_EROGAZIONE_FARM))        return "2.16.840.1.113883.2.9.10.2.6";
    if(code.equals(sDOC_REFERTO_SPECIALISTICO))  return "2.16.840.1.113883.2.9.10.2.19";
    if(code.equals(sDOC_ESENZIONE_DA_REDDITO))   return "2.16.840.1.113883.2.9.10.2.4";
    if(code.equals(sDOC_REFERTO_AMBULATORIALE))  return "2.16.840.1.113883.2.9.10.1.9.1";
    if(code.equals(sDOC_EROGAZIONE_SPEC))        return "2.16.840.1.113883.2.9.10.2.7";
    if(code.equals(sDOC_PROMEMORIA_PREN_CUP))    return "2.16.840.1.113883.2.9.10.2.24";
    if(code.equals(sDOC_ANNULLAMENTO))           return "2.16.840.1.113883.2.9.10.2.25";
    if(code.equals(sDOC_REFERTO_GENERICO))       return "2.16.840.1.113883.2.9.10.1.9.1";
    if(code.equals(sDOC_EMERGENCY_DATA_SET))     return "2.16.840.1.113883.2.9.10.2.4";
    if(code.equals(sDOC_RICHIESTA_DI_RICOVERO))  return "2.16.840.1.113883.2.9.10.2.7";
    if(code.equals(sDOC_RICHIESTA_TRASPORTO))    return "2.16.840.1.113883.2.9.10.2.7";
    if(code.equals(sDOC_VACCINAZIONI))           return "2.16.840.1.113883.2.9.10.2.6";
    if(code.equals(sDOC_SCHEDA_VACCINALE))       return "2.16.840.1.113883.2.9.10.1.6.1";
    if(code.equals(sDOC_CERTIFICATO_VACCINALE))  return "2.16.840.1.113883.2.9.10.1.6.1";
    if(code.equals(sDOC_DIGITAL_GREEN_CERT))     return "2.16.840.1.113883.2.9.10.1.6.1";
    if(code.equals(sDOC_CERTIFICATO_GUARIGIONE)) return "2.16.840.1.113883.2.9.10.1.6.1";
    return "";
  }
  
  @Override
  public String getTemplateId(String code) {
    if(code == null || code.length() == 0) {
      return "";
    }
    if(code.equals(sDOC_PRESCRIZIONE_FARM))      return "ITPRF_PRESC_FARMA-001";
    if(code.equals(sDOC_PRESCRIZIONE_FARM_OBS))  return "ITPRF_PRESC_FARMA-001";
    if(code.equals(sDOC_PROFILO_SANITARIO_SIN))  return "ITPRF_PSUM_SSI-001";
    if(code.equals(sDOC_REFERTO_LABORATORIO))    return "ITPRF_REF_LABCH-001";
    if(code.equals(sDOC_PRESCRIZIONE_APPAR_MED)) return "ITPRF_PRESC_MED-001";
    if(code.equals(sDOC_LETTERA_DIM_OSP))        return "ITPRF_LETTDIM-001";
    if(code.equals(sDOC_VERBALE_PRONTO_SOCC))    return "ITPRF_REFER_PS-001";
    if(code.equals(sDOC_REFERTO_RADIOLOGIA))     return "ITPRF_REF_RADIO-001";
    if(code.equals(sDOC_REFERTO_ANATOMIA_PAT))   return "ITPRF_REF_LABIST-001";
    if(code.equals(sDOC_REGISTRAZIONE_CONSENSO)) return "ITPRF_GEST_CONS-001";
    if(code.equals(sDOC_CERTIFICATO_MALATTIA))   return "ITPRF_CERT_INPS-001";
    if(code.equals(sDOC_PRESCRIZIONE_SPEC))      return "ITPRF_PRESC_SPEC-001";
    if(code.equals(sDOC_EROGAZIONE_FARM))        return "ITPRF_EROG_FARMA-001";
    if(code.equals(sDOC_REFERTO_SPECIALISTICO))  return "ITPRF_REF_SPEC-001";
    if(code.equals(sDOC_ESENZIONE_DA_REDDITO))   return "ITPRF_ESE_RED-001";
    if(code.equals(sDOC_REFERTO_AMBULATORIALE))  return "ITPRF_REF_AMB-001";
    if(code.equals(sDOC_EROGAZIONE_SPEC))        return "ITPRF_EROG_SPEC-001";
    if(code.equals(sDOC_PROMEMORIA_PREN_CUP))    return "ITPRF_PRENOTAZIONE-001";
    if(code.equals(sDOC_ANNULLAMENTO))           return "ITPRF_ANNULLAMENTO-001";
    if(code.equals(sDOC_REFERTO_GENERICO))       return "ITPRF_REF_GEN-001";
    if(code.equals(sDOC_EMERGENCY_DATA_SET))     return "ITPRF_PSUM_EDS-001";
    if(code.equals(sDOC_RICHIESTA_DI_RICOVERO))  return "ITPRF_PRESC_RICO-001";
    if(code.equals(sDOC_RICHIESTA_TRASPORTO))    return "ITPRF_PRESC_TRAS-001";
    if(code.equals(sDOC_VACCINAZIONI))           return "ITPRF_VACC-001";
    if(code.equals(sDOC_SCHEDA_VACCINALE))       return "ITPRF_SCHEDA_VAC-001";
    if(code.equals(sDOC_CERTIFICATO_VACCINALE))  return "ITPRF_CERT_VAC-001";
    return "";
  }
}
