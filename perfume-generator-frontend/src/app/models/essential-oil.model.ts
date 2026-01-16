export interface EssentialOil {
  id: number;
  name: string;
  noteType: 'TOP' | 'HEART' | 'BASE';
  power: number;
  maxPercent: number;
  imageUrl: string;
}
