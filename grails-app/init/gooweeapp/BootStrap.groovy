package gooweeapp

import goowee.core.ApplicationService
import goowee.security.SecurityService
import grails.web.servlet.mvc.GrailsHttpSession
import jakarta.servlet.ServletContext

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
            commonService.devInstall(tenantId)
        }

        applicationService.beforeInit {
            // no-op
        }

        applicationService.onInit {
            applicationService.registerPrettyPrinter(TCompany, '${it.name}')
            applicationService.registerPrettyPrinter(TProduct, '${it.ref} - ${it.name}')

            applicationService.registerCredits('Project Management', 'Gianluca Sartori')
            applicationService.registerCredits('Software Development', 'Francesco Piceghello', 'Gianluca Sartori')
            applicationService.registerCredits('Testing', 'Francesco Piceghello', 'Gianluca Sartori')

            applicationService.registerFeature(
                    controller: 'order',
                    icon: 'fa-flag',
                    favourite: true,
            )
            applicationService.registerFeature(
                    controller: 'config',
            )
            applicationService.registerFeature(
                    parent: 'config',
                    controller: 'company',
                    icon: 'fa-house-flag',
            )
            applicationService.registerFeature(
                    parent: 'config',
                    controller: 'product',
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
