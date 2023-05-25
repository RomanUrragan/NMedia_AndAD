package ru.netology.nmedia.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import kotlinx.coroutines.flow.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.MediaUpload
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val postDao: PostDao,
) : PostRepository {
    override val data = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = {
            postDao.getAllPagingSource()
        }).flow
        .map { pagingData ->
            pagingData.map { postEntity ->
                postEntity.toDto()
            }
        }


    override suspend fun getAll() {
            postDao.getAll()
    }

    override suspend fun save(post: Post, upload: MediaUpload?) {
            postDao.insert(PostEntity.fromDto(post))
    }

    override suspend fun removeById(id: Long) {
        postDao.removeById(id)
    }

    override suspend fun likeById(id: Long) {
        TODO("Not yet implemented")
    }
}
