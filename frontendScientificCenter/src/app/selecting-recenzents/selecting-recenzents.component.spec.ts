import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectingRecenzentsComponent } from './selecting-recenzents.component';

describe('SelectingRecenzentsComponent', () => {
  let component: SelectingRecenzentsComponent;
  let fixture: ComponentFixture<SelectingRecenzentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectingRecenzentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectingRecenzentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
