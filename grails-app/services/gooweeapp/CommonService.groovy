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

@Slf4j
@CompileStatic
class CommonService {

    SecurityService securityService
    TenantService tenantService
    TenantPropertyService tenantPropertyService
    QuantityService quantityService
    CompanyService companyService
    ProductService productService
    OrderService orderService
    OrderItemService orderItemService

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

        securityService.updateGroup(tenantId: tenantService.currentTenantId, name: 'USERS', landingPage: 'order')

        quantityService.enableUnit(QuantityUnit.PCS)
    }

    void devInstall(String tenantId) {
        def myCompany = companyService.create(failOnError: true, name: 'My Company Inc.', isOwned: true, isClient: false, isSupplier: false)
        def showerWorld = companyService.create(failOnError: true, name: 'Shower World Inc.', isOwned: false, isClient: true, isSupplier: false)
        def showerLand = companyService.create(failOnError: true, name: 'Shower Land Inc.', isOwned: false, isClient: true, isSupplier: false)
        def showerTower = companyService.create(failOnError: true, name: 'Shower Tower Inc.', isOwned: false, isClient: true, isSupplier: false)

        def parmenide = productService.create(failOnError: true, ref: 'P123', name: 'PARMENIDE Shower Kit')
        def aristotele = productService.create(failOnError: true, ref: 'A456', name: 'ARISTOTELE Shower Kit')
        def platone = productService.create(failOnError: true, ref: 'L789', name: 'PLATONE Shower Kit')

        def o1 = orderService.create(failOnError: true, supplier: myCompany, client: showerLand, ref: '0001', subject: 'The ROSSI house')
        def o2 = orderService.create(failOnError: true, supplier: myCompany, client: showerWorld, ref: '0002', subject: 'The BIANCHI house')
        def o3 = orderService.create(failOnError: true, supplier: myCompany, client: showerTower, ref: '0003', subject: 'The VERDI house')

        orderItemService.create(failOnError: true, order: o1, product: parmenide, unitPrice: new Money(100), quantity: new Quantity(12))
        orderItemService.create(failOnError: true, order: o1, product: aristotele, unitPrice: new Money(200), quantity: new Quantity(3))
        orderItemService.create(failOnError: true, order: o2, product: parmenide, unitPrice: new Money(100), quantity: new Quantity(6))
        orderItemService.create(failOnError: true, order: o2, product: aristotele, unitPrice: new Money(200), quantity: new Quantity(6))
        orderItemService.create(failOnError: true, order: o2, product: platone, unitPrice: new Money(300), quantity: new Quantity(6))
        orderItemService.create(failOnError: true, order: o3, product: parmenide, unitPrice: new Money(100), quantity: new Quantity(1))
        orderItemService.create(failOnError: true, order: o3, product: aristotele, unitPrice: new Money(200), quantity: new Quantity(2))
        orderItemService.create(failOnError: true, order: o3, product: platone, unitPrice: new Money(300), quantity: new Quantity(3))
    }

}
