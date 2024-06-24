import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../video.service";
import {UserService} from "../user.service";

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrl: './video-detail.component.css'
})
export class VideoDetailComponent implements OnInit {
  videoId!: string;
  videoUrl!: string;
  videoAvailable!: boolean;
  tags: string[] = [];
  videoTitle!: string;
  videoDescription!: string;
  likes!: number;
  dislikes!: number;
  userId!: string;
  viewCount!: number;
  showUnSubscribeButton!: boolean;
  showSubscribeButton!: boolean;

  constructor(private activatedRoute: ActivatedRoute,
              private videoService: VideoService,
              private userService: UserService) {

    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    if (this.videoId) {
      this.videoService.getVideo(this.videoId).subscribe(data => {
        this.videoUrl = data.videoUrl;
        this.videoAvailable = true;
        this.videoTitle = data.title;
        this.videoDescription = data.description;
        this.tags = data.tags;
        this.likes = data.likes;
        this.dislikes = data.dislikes;
        this.userId = data.userId;
        this.viewCount = data.viewCount;
      });
    }
  }

  ngOnInit(): void {
  }

  likeVideo() {
    this.videoService.likeVideo(this.videoId).subscribe(data => {
      this.likes = data.likes;
      this.dislikes = data.dislikes;
    })
  }

  dislikeVideo() {
    this.videoService.dislikeVideo(this.videoId).subscribe(data => {
      this.likes = data.likes;
      this.dislikes = data.dislikes;
    })
  }

  subscribeToUser() {
    let userId = this.userService.getUserId();
    this.userService.subscribeToUser(userId).subscribe(data => {
      this.showUnSubscribeButton = true;
      this.showSubscribeButton = false;
    })
  }

  unSubscribeToUser() {
    let userId = this.userService.getUserId();
    this.userService.unSubscribeUser(userId).subscribe(data => {
      this.showUnSubscribeButton = false;
      this.showSubscribeButton = true;
    })
  }
}
