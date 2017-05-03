package org.jdc.template.ux.about

import me.eugeniomarletti.extras.ActivityCompanion

class AboutContract {
    interface View {
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, AboutActivity::class)

    object IntentOptions
}
