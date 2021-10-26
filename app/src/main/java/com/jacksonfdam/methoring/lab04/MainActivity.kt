package com.jacksonfdam.methoring.lab04

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jacksonfdam.methoring.lab04.MainActivity.Companion.HELLO
import com.jacksonfdam.methoring.lab04.MainActivity.Companion.LOVE
import com.jacksonfdam.methoring.lab04.databinding.ActivityMainBinding
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Qualifier

class MainActivity : AppCompatActivity() {

    /*
    was
    @Inject @field:Named(LOVE) lateinit var infoLove: Info
    @Inject @field:Named(HELLO) lateinit var infoHello: Info
    */

    @Inject
    @field:Choose(LOVE)
    lateinit var infoLove: Info

    @Inject
    @field:Choose(HELLO)
    lateinit var infoHello: Info

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        DaggerMagicBox.create().poke(this)
        setContentView(view)
        view.textView.text = "${infoLove.text} ${infoHello.text}"
    }

    companion object {
        const val LOVE = "Love"
        const val HELLO = "Hello"
    }
}

/*
was
@Module
open class Bag {
    @Provides @Named(LOVE)
    fun sayLoveDagger2(): Info {
        return Info("Love Dagger 2")
    }
    @Provides @Named(HELLO)
    fun sayHelloDagger2(): Info {
        return Info("Hello Dagger 2")
    }
}
*/

@Module
open class Bag {
    @Provides
    @Choose(LOVE)
    fun sayLoveDagger2(): Info {
        return Info("I Love You")
    }

    @Provides
    @Choose(HELLO)
    fun sayHelloDagger2(): Info {
        return Info("Hello Dagger 2")
    }
}

class Info(val text: String)

// @Component(modules = [Bag::class, OtherBag::class, MoreBag::Class])
@Component(modules = [Bag::class])
interface MagicBox {
    fun poke(app: MainActivity)
}

/*
TL;DR
@Qualifier in itself is not used directly. But it’s is used to generate annotation that could help differentiate the same type of class object with different argument sent to it’s constructor.
It is a relatively low profile annotation, and only useful if you have same constructor.

*/
@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Choose(val value: String = "")
