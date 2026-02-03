export interface OilPercentage {
  oilName: string;
  percentage: number;
  imageUrl?: string;
}

export interface PerfumeResponse {
  composition: OilPercentage[];
}
