package template

import goowee.commons.utils.LogUtils
import goowee.elements.components.TableRow
import goowee.elements.contents.ContentCreate
import goowee.elements.contents.ContentEdit
import goowee.elements.contents.ContentTable
import goowee.elements.controls.TextField
import goowee.elements.ElementsController
import goowee.elements.style.TextDefault
import grails.plugin.springsecurity.annotation.Secured
import groovy.util.logging.Slf4j

import jakarta.annotation.PostConstruct
import template.ProductService

@Slf4j
@Secured(['ROLE_USER'])
class ProductController implements ElementsController {

    ProductService productService

    @PostConstruct
    void init() {
        // Executes only once when the application starts
    }

    def handleException(Exception e) {
        // Display a popup message instead of the "Error" screen
        log.error LogUtils.logStackTrace(e)
        display exception: e
    }

    def index() {
        def c = createContent(ContentTable)
        c.table.with {
            filters.with {
                addField(
                        class: TextField,
                        id: 'find',
                        label: TextDefault.FIND,
                )
            }
            sortable = [
                    name: 'asc',
            ]
            columns = [
                    'ref',
                    'name',
            ]

            body.eachRow { TableRow row, Map values ->
                // Do not execute slow operations here to avoid slowing down the table rendering
            }
        }

        c.table.body = productService.list(c.table.filterParams, c.table.fetchParams)
        c.table.paginate = productService.count(c.table.filterParams)

        display content: c
    }

    private buildForm(TProduct obj = null, Boolean readonly = false) {
        def c = obj
                ? createContent(ContentEdit)
                : createContent(ContentCreate)

        if (readonly) {
            c.header.removeNextButton()
            c.form.readonly = true
        }

        c.form.with {
            validate = TProduct
            addField(
                    class: TextField,
                    id: 'ref',
            )
            addField(
                    class: TextField,
                    id: 'name',
            )
        }

        if (obj) {
            c.form.values = obj
        }

        return c
    }

    def create() {
        def c = buildForm()
        display content: c, modal: true
    }

    def onCreate() {
        def obj = productService.create(params)
        if (obj.hasErrors()) {
            display errors: obj
            return
        }

        display action: 'index'
    }

    def edit() {
        def obj = productService.get(params.id)
        def c = buildForm(obj)
        display content: c, modal: true
    }

    def onEdit() {
        def obj = productService.update(params)
        if (obj.hasErrors()) {
            display errors: obj
            return
        }

        display action: 'index'
    }

    def onDelete() {
        try {
            productService.delete(params.id)
            display action: 'index'

        } catch (e) {
            display exception: e
        }
    }
}
