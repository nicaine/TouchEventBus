package mobile.yy.com.touchsample.touch

import android.view.MotionEvent
import mobile.yy.com.toucheventbus.touchBus.AbstractTouchEventHandler
import mobile.yy.com.toucheventbus.touchBus.TouchEventHandler
import mobile.yy.com.toucheventbus.touchBus.TouchViewHolder
import mobile.yy.com.touchsample.ui.FakeMenu

/**
 * Created by 张宇 on 2018/4/26.
 * E-mail: zhangyu4@yy.com
 * YY: 909017428
 */
class MenuTouchHandler : AbstractTouchEventHandler<FakeMenu>() {

    private var touchForMenu = false
    private var lastX = 0f

    override fun onTouch(ui: FakeMenu, e: MotionEvent, hasBeenIntercepted: Boolean): Boolean {
        super.onTouch(ui, e, hasBeenIntercepted)
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                if (e.x < 100 || ui.isOpenOrOpening()) {
                    touchForMenu = true
                    lastX = e.x
                    ui.down()
                } else {
                    touchForMenu = false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (touchForMenu) {
                    ui.move(e.x - lastX)
                    lastX = e.x
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (touchForMenu) ui.up()
            }
        }
        return touchForMenu
    }

    override fun defineNextHandlers(handlers: MutableList<Class<out TouchEventHandler<*, out TouchViewHolder<*>>>>) {
        handlers.add(BackgroundImageTouchHandler::class.java)
        handlers.add(TabTouchHandler::class.java)
        handlers.add(ZoomTextTouchHandler::class.java)
    }

    override fun name() = "MenuTouchHandler"
}