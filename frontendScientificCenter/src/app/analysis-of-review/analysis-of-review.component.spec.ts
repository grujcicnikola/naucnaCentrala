import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisOfReviewComponent } from './analysis-of-review.component';

describe('AnalysisOfReviewComponent', () => {
  let component: AnalysisOfReviewComponent;
  let fixture: ComponentFixture<AnalysisOfReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnalysisOfReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnalysisOfReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
