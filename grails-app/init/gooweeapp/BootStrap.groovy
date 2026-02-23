package gooweeapp

import goowee.core.ApplicationService
import goowee.security.SecurityService
import grails.web.servlet.mvc.GrailsHttpSession
import jakarta.servlet.ServletContext
import template.TTplCompany
import template.TTplProduct

class BootStrap {

    ServletContext servletContext
    ApplicationService applicationService
    SecurityService securityService
    CommonService commonService

    def init = {

        applicationService.onInstall {
            commonService.install()
        }

        applicationService.onTenantInstall { String tenantId ->
            commonService.tenantInstall()
        }

        applicationService.onDevInstall { String tenantId ->
            commonService.devInstall()
        }

        applicationService.beforeInit {
            // no-op
        }

        applicationService.onInit {
            applicationService.registerPrettyPrinter(TTplCompany, '${it.name}')
            applicationService.registerPrettyPrinter(TTplProduct, '${it.ref} - ${it.name}')

            applicationService.registerCredits('Project Management', 'Gianluca Sartori')
            applicationService.registerCredits('Software Development', 'Francesco Piceghello', 'Gianluca Sartori')
            applicationService.registerCredits('Testing', 'Francesco Piceghello', 'Gianluca Sartori')

            // Main application features
            applicationService.registerFeature(
                    controller: 'tplOrder',
                    icon: 'fa-flag',
                    favourite: true,
            )

            // Template features as an example
            applicationService.registerFeature(
                    controller: 'tplTemplate',
            )
            applicationService.registerFeature(
                    parent: 'tplTemplate',
                    controller: 'tplCompany',
                    icon: 'fa-house-flag',
            )
            applicationService.registerFeature(
                    parent: 'tplTemplate',
                    controller: 'tplProduct',
                    icon: 'fa-heart',
            )
        }

        applicationService.onTenantInit {
            // no-op
        }

        applicationService.afterInit { String tenantId ->
            // no-op
        }

        securityService.afterLogin { String tenantId, GrailsHttpSession session ->
            // no-op
        }

        securityService.afterLogout { String tenantId ->
            // no-op
        }
    }

    def destroy = {
    }

}
