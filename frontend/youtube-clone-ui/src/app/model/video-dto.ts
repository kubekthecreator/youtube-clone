export interface VideoDto {
  id: string;
  title: string;
  description: string;
  tags: Array<string>;
  videoUrl: string;
  videoStatus: string;
  thumbnailUrl: string;
  likes: number;
  dislikes: number;
  userId: string;
  viewCount: number;
}
