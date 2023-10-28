package com.irudaru.depcalculator.di

import com.irudaru.depcalculator.data.deposit.DepositDao
import com.irudaru.depcalculator.data.deposit.OfflineDepositsRepository
import com.irudaru.depcalculator.data.local.database.DepositDatabase
import com.irudaru.depcalculator.ui.deposititem.DepositItemViewModel
import com.irudaru.depcalculator.ui.depositlist.DepositListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        listOf(
            databaseModule,
            repositoryModule,
            viewModelModule
        )
    )
}

private val databaseModule = module {
//    single {
//        Room.databaseBuilder(get(), DepositDatabase::class.java, DEPOSIT_DATABASE_NAME).build()
//    }
    single<DepositDatabase> { DepositDatabase.getDatabase(androidContext()) }
    single<DepositDao> { get<DepositDatabase>().depositDao() }

    //single<DepositDao> { DepositDatabase.getDatabase(androidContext()).depositDao() }
}

private val repositoryModule = module {
    single<OfflineDepositsRepository> { OfflineDepositsRepository(get()) }
}

private val viewModelModule = module {
    viewModel<DepositListViewModel> { DepositListViewModel(get()) }
    viewModel<DepositItemViewModel> { DepositItemViewModel(get(), get()) }
}