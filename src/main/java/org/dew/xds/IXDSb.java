package org.dew.xds;

import org.dew.auth.AuthAssertion;
import org.dew.ebxml.ObjectRefList;
import org.dew.ebxml.query.AdhocQueryRequest;
import org.dew.ebxml.query.AdhocQueryResponse;
import org.dew.ebxml.rs.RegistryResponse;

public
interface IXDSb
{
	// ITI-18
	public AdhocQueryResponse  registryStoredQuery(AdhocQueryRequest request, AuthAssertion[] arrayOfAssertion);
	
	// ITI-41
	public RegistryResponse    provideAndRegisterDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion);
	
	// ITI-42
	public RegistryResponse    registerDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion);
	
	// ITI-43
	public XDSDocumentResponse retrieveDocumentSet(XDSDocumentRequest request, AuthAssertion[] arrayOfAssertion);
	
	// ITI-57
	public RegistryResponse    updateDocumentSet(XDSDocument[] arrayOfXDSDocument, AuthAssertion[] arrayOfAssertion);
	
	// ITI-62
	public RegistryResponse    deleteDocumentSet(ObjectRefList objectRefList, AuthAssertion[] arrayOfAssertion);
}
