package gooweeapp

import goowee.properties.TenantPropertyService
import goowee.security.SecurityService
import goowee.tenants.TenantService
import goowee.types.Money
import goowee.types.Quantity
import goowee.types.QuantityService
import goowee.types.QuantityUnit
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import jakarta.annotation.PostConstruct
import template.TplCompanyService
import template.TplOrderItemService
import template.TplOrderService
import template.TplProductService

@Slf4j
@CompileStatic
class CommonService {

    SecurityService securityService
    TenantService tenantService
    TenantPropertyService tenantPropertyService
    QuantityService quantityService

    TplCompanyService tplCompanyService
    TplProductService tplProductService
    TplOrderService tplOrderService
    TplOrderItemService tplOrderItemService

    @PostConstruct
    void init() {
        // Executes only once when the application starts
    }

    void install() {
        // no-op
    }

    void tenantInstall() {
        tenantPropertyService.setString('PRIMARY_BACKGROUND_COLOR', '#255aa8')
        tenantPropertyService.setString('LOGIN_COPY', 'Copyright &copy; <a href="https://goowee.org">The Goowee Team</a><br/>All rights reserved')

        securityService.updateGroup(tenantId: tenantService.currentTenantId, name: 'USERS', landingPage: 'tplOrder')

        quantityService.enableUnit(QuantityUnit.PCS)
    }

    void devInstall() {
        def myCompany = tplCompanyService.create(failOnError: true, name: 'My Company Inc.', isOwned: true, isClient: false, isSupplier: false)
        def showerWorld = tplCompanyService.create(failOnError: true, name: 'Shower World Inc.', isOwned: false, isClient: true, isSupplier: false)
        def showerLand = tplCompanyService.create(failOnError: true, name: 'Shower Land Inc.', isOwned: false, isClient: true, isSupplier: false)
        def showerTower = tplCompanyService.create(failOnError: true, name: 'Shower Tower Inc.', isOwned: false, isClient: true, isSupplier: false)

        def parmenide = tplProductService.create(failOnError: true, ref: 'P123', name: 'PARMENIDE Shower Kit')
        def aristotele = tplProductService.create(failOnError: true, ref: 'A456', name: 'ARISTOTELE Shower Kit')
        def platone = tplProductService.create(failOnError: true, ref: 'L789', name: 'PLATONE Shower Kit')

        def o1 = tplOrderService.create(failOnError: true, supplier: myCompany, client: showerLand, ref: '0001', subject: 'The ROSSI house')
        def o2 = tplOrderService.create(failOnError: true, supplier: myCompany, client: showerWorld, ref: '0002', subject: 'The BIANCHI house')
        def o3 = tplOrderService.create(failOnError: true, supplier: myCompany, client: showerTower, ref: '0003', subject: 'The VERDI house')

        tplOrderItemService.create(failOnError: true, order: o1, product: parmenide, unitPrice: new Money(100), quantity: new Quantity(12))
        tplOrderItemService.create(failOnError: true, order: o1, product: aristotele, unitPrice: new Money(200), quantity: new Quantity(3))
        tplOrderItemService.create(failOnError: true, order: o2, product: parmenide, unitPrice: new Money(100), quantity: new Quantity(6))
        tplOrderItemService.create(failOnError: true, order: o2, product: aristotele, unitPrice: new Money(200), quantity: new Quantity(6))
        tplOrderItemService.create(failOnError: true, order: o2, product: platone, unitPrice: new Money(300), quantity: new Quantity(6))
        tplOrderItemService.create(failOnError: true, order: o3, product: parmenide, unitPrice: new Money(100), quantity: new Quantity(1))
        tplOrderItemService.create(failOnError: true, order: o3, product: aristotele, unitPrice: new Money(200), quantity: new Quantity(2))
        tplOrderItemService.create(failOnError: true, order: o3, product: platone, unitPrice: new Money(300), quantity: new Quantity(3))
    }

}
