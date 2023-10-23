package com.example.ui.homepage


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.ourstoryapps.DataDummy
import com.example.ourstoryapps.MainDispactherRule
import com.example.ourstoryapps.data.AkunRepository
import com.example.ourstoryapps.data.model.ListStoryItem
import com.example.ourstoryapps.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomepageViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainDispactherRule()

    @Mock private lateinit var AkunStoryRepository: AkunRepository

    @Test
    fun `when Get Pagging Story Should Not Null and Return Data`() = runTest {


        val dummyQuote = DataDummy.generateDummyQuoteResponse()
        val data: PagingData<ListStoryItem> = StoryOurPagingSource.snapshot(dummyQuote)
        val expectedQuote = MutableLiveData<PagingData<ListStoryItem>>()
        expectedQuote.value = data
        `when`(AkunStoryRepository.getStory()).thenReturn(expectedQuote)

        val mainViewModel = HomepageViewModel(AkunStoryRepository)
        val actualQuote: PagingData<ListStoryItem> = mainViewModel.storyPagging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = HomePageAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)


        Assert.assertNotNull(differ.snapshot())
            Assert.assertEquals(dummyQuote.size, differ.snapshot().size)
                Assert.assertEquals(dummyQuote[0], differ.snapshot()[0])



    }

    @Test
    fun `when Get Pagging Story Empty Should Return No Data`() = runTest {
        val data: PagingData<ListStoryItem> = PagingData.from(emptyList())
        val expectedQuote = MutableLiveData<PagingData<ListStoryItem>>()
        expectedQuote.value = data
        `when`(AkunStoryRepository.getStory()).thenReturn(expectedQuote)

        val mainViewModel = HomepageViewModel(AkunStoryRepository)
        val actualQuote: PagingData<ListStoryItem> = mainViewModel.storyPagging.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = HomePageAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertEquals(0, differ.snapshot().size)
    }
}


class StoryOurPagingSource : PagingSource<Int, LiveData<List<ListStoryItem>>>() {
    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}