export interface Parcel{
  id: number,
  latitude: number,
  longitude: number,
  humusContent: number,
  name: string,
  expectedWindStrength: WindStrength,
  recommendations: CropRecommendation[],
  manufacturerPreferences: Manufacturer[]
}

export interface CropRecommendation{
  plant: CropType,
  name: string,
  manufacturer: Manufacturer
}

export enum CropType{
  KUKURUZ= "KUKURUZ", SUNCOKRET = "SUNCOKRET", PSENICA = "PSENICA", ULJANA_REPICA = "ULJANA_REPICA", SECERNA_REPA = "SECERNA_REPA", SOJA = "SOJA"
}
export const CropTypeMapping: { [key in CropType]: string } = {
  [CropType.KUKURUZ]: 'Corn',
  [CropType.SUNCOKRET]: 'Sunflower',
  [CropType.PSENICA]: 'Wheat',
  [CropType.ULJANA_REPICA]: 'Rapeseed',
  [CropType.SECERNA_REPA]: 'Sugar Beet',
  [CropType.SOJA]: 'Soybean'
};
export enum WindStrength{
  SLAB = "SLAB", SREDNJI = "SREDNJI", JAK = "JAK"
}
export const WindStrengthMapping: { [key in WindStrength]: string } = {
  [WindStrength.SLAB]: 'Weak',
  [WindStrength.SREDNJI]: 'Medium',
  [WindStrength.JAK]: 'Strong'
};
export enum Manufacturer {
  KWS = "KWS",
  BAYER = "BAYER",
  SES_VANDERHAVE = "SES_VANDERHAVE",
  SYNGENTA = "SYNGENTA",
  LIMAGRAIN = "LIMAGRAIN",
  INVIGOR = "INVIGOR",
  RAGT = "RAGT",
  CORTEVA_AGRISCIENCE = "CORTEVA_AGRISCIENCE",
  MONSANTO = "MONSANTO",
  ANY = "ANY",
  PIONEER = "PIONEER",
  ASGROW = "ASGROW",
  NUSEED = "NUSEED",
  ADVANTA_SEEDS = "ADVANTA_SEEDS",
  DEKALB = "DEKALB"
}
export const ManufacturerMapping: { [key in Manufacturer]: string } = {
  [Manufacturer.KWS]: 'KWS',
  [Manufacturer.BAYER]: 'Bayer',
  [Manufacturer.SES_VANDERHAVE]: 'SES Vanderhave',
  [Manufacturer.SYNGENTA]: 'Sygenta',
  [Manufacturer.LIMAGRAIN]: 'Limagrain',
  [Manufacturer.INVIGOR]: 'InVigor',
  [Manufacturer.RAGT]: 'RAGT',
  [Manufacturer.CORTEVA_AGRISCIENCE]: 'Corteva Agriscience',
  [Manufacturer.MONSANTO]: 'Monsanto',
  [Manufacturer.PIONEER]: 'Pioneer',
  [Manufacturer.ASGROW]: 'Asgrow',
  [Manufacturer.NUSEED]: 'Nuseed',
  [Manufacturer.ADVANTA_SEEDS]: 'Advanta Seeds',
  [Manufacturer.DEKALB]: 'Dekalb',
  [Manufacturer.ANY]: 'Any Manufacturer',
};
export interface CreateParcelDto{
  latitude: number,
  longitude: number,
  humusContent: number,
  lastSowing: SowingEvent,
  expectedWindStrength: WindStrength,
  manufacturerPreferences: Manufacturer[]
}

export interface SowingEvent{
  date: string,
  plant: CropType
}
