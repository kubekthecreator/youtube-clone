import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../video.service";

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

  constructor(private activatedRoute: ActivatedRoute,
  private videoService: VideoService) {

    this.videoId = this.activatedRoute.snapshot.params['videoId'];
    if (this.videoId) {
      this.videoService.getVideo(this.videoId).subscribe(data => {
        this.videoUrl = data.videoUrl;
        this.videoAvailable = true;
        this.videoTitle = data.title;
        this.videoDescription = data.description;
        this.tags = data.tags;
      });
    }
  }

  ngOnInit(): void {
  }

}
