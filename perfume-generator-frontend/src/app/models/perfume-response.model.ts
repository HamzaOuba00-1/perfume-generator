export interface OilPercentage {
  oilName: string;
  percentage: number;
}

export interface PerfumeResponse {
  composition: OilPercentage[];
}
