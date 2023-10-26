package org.dew.xds;

public 
interface OID 
{
  public static final String PERSON_ID      = "2.16.840.1.113883.2.9.4.3.2";
  public static final String GENDERS        = "2.16.840.1.113883.5.1";
  
  public static final String LOINC          = "2.16.840.1.113883.6.1";
  public static final String AIC            = "2.16.840.1.113883.2.9.6.1.5";
  public static final String ATC            = "2.16.840.1.113883.2.9.6.1.12";
  public static final String GEQ            = "2.16.840.1.113883.2.9.6.1.51"; // Gruppo Equivalenza
  public static final String NOM_IT         = "2.16.840.1.113883.2.9.6.1.11";
  public static final String ICD9CM         = "2.16.840.1.113883.6.2"; 
  public static final String SNOMED_CT      = "2.16.840.1.113883.6.96";
  public static final String ICD9CM_NEW     = "2.16.840.1.113883.6.103";
  
  public static final String HSP11          = "2.16.840.1.113883.2.9.4.1.2";
  public static final String STS11          = "2.16.840.1.113883.2.9.4.1.3";
  public static final String RIA11          = "2.16.840.1.113883.2.9.4.1.5";
  
  public static final String SSN_PRESCR_ID  = "2.16.840.1.113883.2.9.4.3.4";
  public static final String SASN_PRESCR_ID = "2.16.840.1.113883.2.9.4.3.5";
  public static final String NRE            = "2.16.840.1.113883.2.9.4.3.8";
  
  public static final String CONF_CODES     = "2.16.840.1.113883.5.25";
  public static final String TYPE_CODES     = LOINC;
  public static final String CLASS_CODES    = "2.16.840.1.113883.2.9.3.3.6.1.5";
  public static final String FORMAT_CODES   = "2.16.840.1.113883.2.9.3.3.6.1.6";
  public static final String FACILITY_CODES = "2.16.840.1.113883.2.9.3.3.6.1.1";
  public static final String PRACTICE_CODES = "2.16.840.1.113883.2.9.3.3.6.1.2";
  public static final String CON_TYPE_CODES = "2.16.840.1.113883.2.9.3.3.6.1.4";
  
  public static final String TEMPL_IT_ROOT  = "2.16.840.1.113883.2.9.10.2";
  public static final String TEMPL_CERT_INF = TEMPL_IT_ROOT + ".5";     // ITPRF_CERT_INAIL-001
  public static final String TEMPL_PRE_FARM = TEMPL_IT_ROOT + ".6";     // ITPRF_PRESC_FARMA-001
  public static final String TEMPL_PRE_SPEC = TEMPL_IT_ROOT + ".7";     // ITPRF_PRESC_SPEC-001
  public static final String TEMPL_PRE_RIC  = TEMPL_IT_ROOT + ".8";     // ITPRF_PRESC_RICO-001
  public static final String TEMPL_REPORT   = TEMPL_IT_ROOT + ".10";    // ITPRF_REF_LABCH-001
  public static final String TEMPL_LDO      = TEMPL_IT_ROOT + ".10.99"; // IT_LETTDIM-001
  public static final String TEMPL_CERT_MAL = TEMPL_IT_ROOT + ".15";    // ITPRF_CERT_INPS-001
  public static final String TEMPL_PAT_SUM  = TEMPL_IT_ROOT + ".21";    // ITPRF_PSUM_SSI-001
  public static final String TEMPL_BOOKING  = TEMPL_IT_ROOT + ".24";    // ITPRF_PRENOTAZIONE-001
  public static final String TEMPL_ANNUL    = TEMPL_IT_ROOT + ".25";    // ITPRF_ANNULLAMENTO-001
  public static final String TEMPL_VERB_PS  = TEMPL_IT_ROOT + ".26";    // ITPRF_REFER_PS-001
}
