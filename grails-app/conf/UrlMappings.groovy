class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        
		"/"(controller:"StatusPortal", action:"index1")
		//"/index" (controller:"StatusPortal", action:"index")
		"500"(view:'/error')
		
		
		
	}
}
