package com.example.storyplayer.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyplayer.data.model.Story
import com.example.storyplayer.data.model.StoryGroupModel

class MainViewModel: ViewModel() {

    val dummyResponse = MutableLiveData<StoryGroupModel>()
    init {
        dummyResponse.value = StoryGroupModel(
            arrayListOf(
                StoryGroupModel.StoryGroup(
                    username = "architect",
                    profileImageUrl = "https://i.pinimg.com/originals/25/5f/36/255f362fcda39fbc5af3b452b6435513.jpg",
                    stories = arrayListOf(
                        Story(
                            "1",
                            "IMAGE",
                            "",
                            "https://wallpaperaccess.com/full/2808693.jpg"
                        )
                    )
                ),
                StoryGroupModel.StoryGroup(
                    username = "oracle",
                    profileImageUrl = "https://static.wikia.nocookie.net/matrix/images/c/c9/The_Oracle.jpg/revision/latest?cb=20130119085243",
                    stories = arrayListOf(
                        Story(
                            "1",
                            "IMAGE",
                            "",
                            "https://media.makeameme.org/created/got-a-question-tuq1vt.jpg"
                        )
                    )
                ),
                StoryGroupModel.StoryGroup(
                    username = "Mr.Smith",
                    profileImageUrl = "https://upload.wikimedia.org/wikipedia/en/1/1f/Agent_Smith_%28The_Matrix_series_character%29.jpg",
                    stories = arrayListOf(
                        Story(
                            "1",
                            "IMAGE",
                            "",
                            "https://i.pinimg.com/originals/af/71/bf/af71bf6fad19226f8a08e710e03f7d07.jpg"
                        )
                    )
                ),
                StoryGroupModel.StoryGroup(
                    username = "neo",
                    profileImageUrl = "https://upload.wikimedia.org/wikipedia/tr/3/3f/Keanumatrix.jpg",
                    stories = arrayListOf(
                        Story(
                            "1",
                            "IMAGE",
                            "",
                            "https://image.winudf.com/v2/image1/Y29tLndhbGx6ei5tYXRyaXhfc2NyZWVuXzBfMTU4MDc3ODc3MF8wMjQ/screen-0.jpg?fakeurl=1&type=.jpg"
                        ), Story(
                            "5",
                            "VIDEO",
                            "https://dm0qx8t0i9gc9.cloudfront.net/watermarks/video/cq8l59W/videoblocks-seamless-video-with-fly-through-abstract-3d-rendering-of-a-scientific-technology-data-binary-code_b-lbknsr2v__45edc145b09ba9f2e678f28dcb6977c0__P360.mp4",
                            ""
                        ), Story(
                            "2",
                            "IMAGE",
                            "",
                            "https://i.pinimg.com/originals/95/98/03/959803f1e3d9c4445673c063122056d7.jpg"
                        ), Story(
                            "3",
                            "IMAGE",
                            "",
                            "https://i.pinimg.com/originals/5b/ce/94/5bce942303764dbe27eb7691c4d7315b.jpg"
                        ), Story(
                            "4",
                            "IMAGE",
                            "",
                            "https://www.wallpapertip.com/wmimgs/73-738031_neo-matrix-hd-4k.jpg"
                        )
                    )
                ),
                StoryGroupModel.StoryGroup(
                    username = "morpheus",
                    profileImageUrl = "https://www.hebergementwebs.com/image/ea/ea47e3b414404300c6fb91fb9c64043b.jpg/matrix-4-we-know-why-laurence-fishburne-morpheus-will-not-be-there.jpg",
                    stories = arrayListOf(
                        Story(
                            "2",
                            "IMAGE",
                            "",
                            "https://d2ofqe7l47306o.cloudfront.net/myfreewallpapers/movies/mobile/morpheus-matrix-reloaded.jpg"
                        ), Story(
                            "3",
                            "IMAGE",
                            "",
                            "https://i.pinimg.com/originals/71/6e/25/716e25ff821c8728d567dcba1b5426dd.jpg"
                        ), Story(
                            "1",
                            "IMAGE",
                            "",
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRK_YOnAOp7Zy69sF9lSTvtH87FgQAvgOo18w&usqp=CAU"
                        ), Story(
                            "5",
                            "VIDEO",
                            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                            ""
                        )
                    )
                ),
                StoryGroupModel.StoryGroup(
                    username = "trinity",
                    profileImageUrl = "https://upload.wikimedia.org/wikipedia/tr/7/7a/MatrixTrinity.jpg",
                    stories = arrayListOf(
                        Story(
                            "12",
                            "IMAGE",
                            "",
                            "https://images.hdqwalls.com/download/matrix-trinity-5k-yq-640x1136.jpg"
                        ), Story(
                            "13",
                            "IMAGE",
                            "",
                            "https://images.hdqwalls.com/walls/thumb/trinity-4k-2020-n0.jpg"
                        ), Story(
                            "11",
                            "IMAGE",
                            "",
                            "https://static2.srcdn.com/wordpress/wp-content/uploads/2020/04/Trinity-Matrix-Featured-Image-1.jpg"
                        )
                    )
                )
            )
        )
    }
}